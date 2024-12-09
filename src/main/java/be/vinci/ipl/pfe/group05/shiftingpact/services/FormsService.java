package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Choice;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.CompaniesRepository;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.FormsRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormsService {

  @Autowired
  FormsRepository repository;
  @Autowired
  CompaniesService companiesService;

  @Autowired
  CompaniesRepository companiesRepository;
  @Autowired
  QuestionsService questionsService;

  private static final List<String> ALL_TEMPLATES = List.of(
      "WORKERS",
      "PRODUCTS",
      "FACILITY",
      "OWNED FACILITY",
      "ALL"
  );


  private int calculateProgression(Form form) {
    int completed = form.getCompleted();
    int total = form.getTotal();
    if (total == 0) {
      return 0;
    }
    // return (int) ((double) completed / total * 100);
    return completed / total * 100;
  }

  public List<Form> getAllFormsInProgress(int companyId) {
    List<Form> allFormsInProgress = repository.findByCompanyId(companyId);

    for (Form form : allFormsInProgress) {
      int progression = calculateProgression(form);
      form.setProgression(progression);
    }
    return allFormsInProgress;
  }


  public Form updateProgression(int formId) {
    Form form = repository.findByFormId(formId).orElse(null);
    if (form == null) {
      throw new IllegalArgumentException("Formulaire introuvable");
    }
    int progression = calculateProgression(form);
    form.setProgression(progression);
    repository.save(form);
    return form;
  }


  public Form createOne(Integer companyId){
    Form form = new Form();
    form.setCompanyId(companyId);
    Company company = companiesService.getOneById(companyId);


    List<String> companyTemplates = new ArrayList<>();
    if (company.getNumberOfWorkers() > 0) companyTemplates.add("WORKERS");
    if (company.isSellsProduct()) companyTemplates.add("PRODUCTS");
    if (company.isOwner()) companyTemplates.add("OWNED FACILITY");
    else companyTemplates.add("FACILITY");
    companyTemplates.add("ALL");
    company.setTemplates(companyTemplates);

    List<Question> questionList = new ArrayList<>();

    for (String temp : companyTemplates){
      List<Question> list = questionsService.getAllByTemplate(temp);
      questionList.addAll(list);
    }

    List<String> otherTemplates = new ArrayList<>(ALL_TEMPLATES);
    otherTemplates.removeAll(companyTemplates);

    List<Question> otherQuestions = new ArrayList<>();
    for (String template : otherTemplates) {
      otherQuestions.addAll(questionsService.getAllByTemplate(template));
    }

    form.setSubmitted(false);
    form.setOtherQuestions(otherQuestions);
    form.setFormId((int) repository.count()+1);
    form.setQuestionList(questionList);
    form.setAnswersList(new ArrayList<>());
    form.setTotal(questionList.size());
    form.setCompleted(0);
    form.setCreatedAt(LocalDateTime.now());
    companiesRepository.save(company);
    form.setProgression(0);
    return repository.save(form);
  }

  public Iterable<Form> getAllForms() {
    return repository.findAll();
  }

  public Form getOneFormById(int formId) {
    return repository.findByFormId(formId).orElse(null);
  }

  public Form saveAnswers(int formId, List<Answer> answers) {
    Form form = repository.findByFormId(formId).orElse(null);
    if (form == null) {
      throw new IllegalArgumentException("Formulaire introuvable");
    }
    if(form.getSendAt()!=null){
      throw new IllegalArgumentException("Le formulaire a déjà été envoyé");
    }
    List<Answer> existingAnswers = form.getAnswersList();
    for (Answer newAnswer : answers) {
      if (newAnswer.getResponse() != null && !newAnswer.getResponse().isEmpty()) {
        existingAnswers.removeIf(existingAnswer -> existingAnswer.getQuestionId() == newAnswer.getQuestionId());
        existingAnswers.add(newAnswer);
      }
    }

    form.setAnswersList(existingAnswers);
    form.setCompleted(existingAnswers.size());
    return repository.save(form);
  }

  public Form submit(int formId) {
    Form form = repository.findByFormId(formId).orElse(null);
    if (form == null) {
      throw new IllegalArgumentException("Formulaire introuvable");
    }
    if (form.getCompleted() != form.getTotal()) {
      throw new IllegalArgumentException("Le formulaire n'est pas complet"); // mettre un commentaire dans le front qui dit quon a oublier de completer
    }
 //   if(form.getSendAt()!=null){
   //   throw new IllegalArgumentException("Le formulaire a déjà été envoyé");
    //}

    // Calcul des scores pour chaque pilier
    double scoreE = calculateScoreByPillar(form, "E");
    double scoreS = calculateScoreByPillar(form, "S");
    double scoreG = calculateScoreByPillar(form, "G");

    // Calcul du score total ESG
    double scoreESG = scoreE + scoreS + scoreG;

    // Mise à jour des scores dans le formulaire
    form.setScoreE(scoreE);
    form.setScoreS(scoreS);
    form.setScoreG(scoreG);
    form.setScoreESG(scoreESG);


    form.setSendAt(LocalDateTime.now());
    form.setCompleted(form.getTotal());
    form.setSubmitted(true);
    return repository.save(form);
  }


  private double calculateScoreByPillar(Form form, String pillar) {
    if (form == null || form.getAnswersList() == null || form.getQuestionList() == null) {
      throw new IllegalArgumentException("Form or its required data cannot be null");
    }

    // Liste des questions filtrées par pilier
    List<Question> questionsForPillar = form.getQuestionList().stream()
        .filter(question -> question.getPillar().startsWith(pillar))
        .collect(Collectors.toList());

    double totalScore = 0;

    // Pour chaque question, calculer le score total
    for (Question question : questionsForPillar) {
      // Récupérer toutes les réponses associées à la question
      List<Answer> answersForQuestion = form.getAnswersList().stream()
          .filter(answer -> answer.getQuestionId() == question.getQuestionId())
          .collect(Collectors.toList());

      if (!answersForQuestion.isEmpty()) {
        // Calculer la somme des poids des choix sélectionnés
        double totalChoiceWeight = 0;
        for (Answer answer : answersForQuestion) {
          totalChoiceWeight += getChoiceWeight(question, answer.getResponse());
        }

        // Diviser le score total pour cette question par 2
        totalScore += totalChoiceWeight / 2.0;
      }
    }

    return totalScore;
  }


  /**
   * Récupère le poids d'un choix donné dans une question.
   */
  private double getChoiceWeight(Question question, String response) {
    if (response == null || response.isEmpty() || question.getChoice() == null) {
      return 0; // Aucun score si pas de réponse ou pas de choix définis
    }

    // Trouver le poids correspondant au choix donné
    return question.getChoice().stream()
        .filter(choice -> choice.getChoice().equals(response))
        .mapToDouble(Choice::getPoids)
        .findFirst()
        .orElse(0); // Retourne 0 si le choix n'est pas trouvé
  }


  public int getNumberOfSubmittedForms() {
    return repository.findAll().stream()
        .filter(Form::isSubmitted)
        .toList()
        .size();
  }

  public double getAverageScoreESG() {
    List<Form> forms = (List<Form>) repository.findAll();
    double totalScore = 0;
    for (Form form : forms) {
      totalScore += form.getScoreESG();
    }
    return totalScore / forms.size();
  }

  public int getNumberOfFormsInProgress() {
    return (int) repository.findAll().
        stream().filter(form -> !form.isSubmitted()).count();
  }

}