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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
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

  public List<Form> getAllFormsInProgress(int companyId) {
    List<Form> allFormsInProgress = repository.findByCompanyId(companyId);

    for (Form formsInProgress : allFormsInProgress) {
      int totalQuestions = formsInProgress.getTotal();
      int completed = formsInProgress.getCompleted();

      int progression = completed/totalQuestions*100;
      formsInProgress.setProgression(progression);
    }
    return allFormsInProgress;
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
      throw new IllegalArgumentException("Le formulaire n'est pas complet");
    }
    if(form.getSendAt()!=null){
      throw new IllegalArgumentException("Le formulaire a déjà été envoyé");
    }
    form.setSendAt(LocalDateTime.now());
    form.setCompleted(form.getTotal());
    form.setSubmitted(true);
    return repository.save(form);
  }


  private double calculateScore(Form form) {
    if (form == null || form.getAnswersList() == null || form.getQuestionList() == null) {
      throw new IllegalArgumentException("Form or its required data cannot be null");
    }

    // Préparer une Map pour associer questionId -> Answer
    Map<Integer, Answer> answerMap = form.getAnswersList().stream()
        .collect(Collectors.toMap(Answer::getQuestionId, Function.identity()));

    // Calculer le score total
    return form.getQuestionList().stream()
        .mapToDouble(question -> {
          int questionWeight = question.getPoids(); // Poids de la question
          Answer answer = answerMap.get(question.getQuestionId()); // Recherche O(1)

          if (answer != null && answer.getResponse() != null) {
            // Récupérer le poids du choix
            int choiceWeight = getChoiceWeight(question, answer.getResponse());
            // Retourner le score du choix, en respectant le poids maximum de la question
            return Math.min(choiceWeight, questionWeight); // Ne pas dépasser le poids max de la question
          }

          return 0; // Si aucune réponse ou réponse invalide
        })
        .sum();
  }


  /**
   * Récupère le poids d'un choix donné dans une question.
   */
  private int getChoiceWeight(Question question, String response) {
    if (question.getChoice() == null) {
      return 0; // Aucun choix défini
    }
    return question.getChoice().stream()
        .filter(choice -> choice.getChoice().equals(response))
        .map(Choice::getPoids)
        .findFirst()
        .orElse(0); // Retourne 0 si la réponse ne correspond à aucun choix
  }


}

// TODO 2
