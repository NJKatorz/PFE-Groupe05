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


  /**
   * Calculates the progression of a form based on the number of questions answered
   *
   * @param form the form to calculate the progression
   * @return the progression in percentage
   */
  private int calculateProgression(Form form) {
    int completed = form.getCompleted();
    int total = form.getTotal();
    if (total == 0) {
      return 0;
    }
    // return (int) ((double) completed / total * 100);
    return completed / total * 100;
  }

  /**
   * Get all forms in progress for a company by its id
   *
   * @param companyId the id of the company
   * @return the list of forms in progress
   */
  public List<Form> getAllFormsInProgress(int companyId) {
    List<Form> allFormsInProgress = repository.findByCompanyId(companyId);

    for (Form form : allFormsInProgress) {
      int progression = calculateProgression(form);
      form.setProgression(progression);
    }
    return allFormsInProgress;
  }

  /**
   * Get the form in progress for a company by its id
   *
   * @param companyID the id of the company
   * @return the form in progress or null if there is no form in progress
   */
  public Form getFormByCompanyId(int companyID) {
    return repository.findByCompanyId(companyID).stream().findFirst().orElse(null);
  }

  /**
   * Create a form for a company by its id
   *
   * @param companyId the id of the company
   * @return the form created for an associated company
   */
  public Form createOne(Integer companyId) {
    Form form = new Form();
    form.setCompanyId(companyId);
    Company company = companiesService.getOneById(companyId);

    List<String> companyTemplates = new ArrayList<>();
    if (company.getNumberOfWorkers() > 0) {
      companyTemplates.add("WORKERS");
    }
    if (company.isSellsProduct()) {
      companyTemplates.add("PRODUCTS");
    }
    if (company.isOwner()) {
      companyTemplates.add("OWNED FACILITY");
    } else {
      companyTemplates.add("FACILITY");
    }
    companyTemplates.add("ALL");
    company.setTemplates(companyTemplates);

    List<Question> questionList = new ArrayList<>();

    for (String temp : companyTemplates) {
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
    form.setFormId((int) repository.count() + 1);
    form.setQuestionList(questionList);
    form.setAnswersList(new ArrayList<>());
    form.setTotal(questionList.size());
    form.setCompleted(0);
    form.setCreatedAt(LocalDateTime.now());
    companiesRepository.save(company);
    form.setProgression(0);
    return repository.save(form);
  }

  /**
   * Get all forms
   *
   * @return all forms
   */
  public Iterable<Form> getAllForms() {
    return repository.findAll();
  }

  /**
   * Get a form by its id
   *
   * @param formId the id of the form
   * @return the associated form or null if the form is not found
   */
  public Form getOneFormById(int formId) {
    return repository.findByFormId(formId).orElse(null);
  }

  /**
   * Save the answers of a form
   *
   * @param formId  the id of the form
   * @param answers the list of answers to save
   * @return the form with the list of saved answers
   */
  public Form saveAnswers(int formId, List<Answer> answers) {
    Form form = repository.findByFormId(formId).orElse(null);
    if (form == null) {
      throw new IllegalArgumentException("Formulaire introuvable");
    }
    if (form.getSendAt() != null) {
      throw new IllegalArgumentException("Le formulaire a déjà été soumis");
    }
    List<Answer> existingAnswers = form.getAnswersList();
    for (Answer newAnswer : answers) {
      if (newAnswer.getResponse() != null && !newAnswer.getResponse().isEmpty()) {
        existingAnswers.removeIf(
            existingAnswer -> existingAnswer.getQuestionId() == newAnswer.getQuestionId());
        existingAnswers.add(newAnswer);
      }
    }

    form.setAnswersList(existingAnswers);
    form.setCompleted(existingAnswers.size());
    return repository.save(form);
  }

  /**
   * Submit a form by its id It calculates the scores for each pillar and the total ESG score It
   * normalizes the scores by 30 and converts them to percentage It updates the scores in the form
   *
   * @param formId the id of the form
   * @return the submitted form
   */
  public Form submit(int formId) {
    Form form = repository.findByFormId(formId).orElse(null);
    if (form == null) {
      throw new IllegalArgumentException("Formulaire introuvable");
    }
    if (form.getCompleted() != form.getTotal()) {
      throw new IllegalArgumentException(
          "Le formulaire n'est pas complet"); // put a comment in the front that says we forgot to complete

    }
    if (form.getSendAt() != null) {
      throw new IllegalArgumentException("Le formulaire a déjà été soumis");
    }

    // Calculation scores for each pillar
    double scoreE = calculateScoreByPillar(form, "E");
    double scoreS = calculateScoreByPillar(form, "S");
    double scoreG = calculateScoreByPillar(form, "G");

    // Calculations of the total ESG score
    double scoreESG = scoreE + scoreS + scoreG;

    // Normalization of scores by 30 and conversion to percentage
    scoreE = (scoreE / 30) * 100;
    scoreS = (scoreS / 30) * 100;
    scoreG = (scoreG / 30) * 100;
    scoreESG =
        (scoreESG / 90) * 100;  // Divide by 90 (30 * 3) to get the total ESG percentage

    // Update scores in the form
    form.setScoreE(scoreE);
    form.setScoreS(scoreS);
    form.setScoreG(scoreG);
    form.setScoreESG(scoreESG);

    form.setSendAt(LocalDateTime.now());
    form.setCompleted(form.getTotal());
    form.setSubmitted(true);
    return repository.save(form);
  }

  /**
   * Calculates the score for a form based on a pillar
   *
   * @param form   the form to calculate the score
   * @param pillar the pillar to calculate the score
   * @return the score for the pillar
   */
  private double calculateScoreByPillar(Form form, String pillar) {
    if (form == null || form.getAnswersList() == null || form.getQuestionList() == null) {
      throw new IllegalArgumentException("Form or its required data cannot be null");
    }

    // Filter questions by pillar
    List<Question> questionsForPillar = form.getQuestionList().stream()
        .filter(question -> question.getPillar().startsWith(pillar))
        .collect(Collectors.toList());

    double totalScore = 0;

    // For each question, calculate the total score
    for (Question question : questionsForPillar) {
      // Get all answers associated with the question
      List<Answer> answersForQuestion = form.getAnswersList().stream()
          .filter(answer -> answer.getQuestionId() == question.getQuestionId())
          .collect(Collectors.toList());

      if (!answersForQuestion.isEmpty()) {
        // Calculate the sum of the weights of the selected choices
        double totalChoiceWeight = 0;
        for (Answer answer : answersForQuestion) {
          totalChoiceWeight += getChoiceWeight(question, answer.getResponse());
        }

        totalScore += totalChoiceWeight;
      }
    }

    return totalScore;
  }

  /**
   * Récupère le poids d'un choix donné dans une question.
   */
  /**
   * Get the weight of a given choice in a question.
   *
   * @param question the question to get the weight
   * @param response the response to get the weight
   * @return the weight of the choice
   */
  private double getChoiceWeight(Question question, String response) {
    if (response == null || response.isEmpty() || question.getChoice() == null) {
      return 0; // No score if no answer or no choices defined
    }

    // Trouver le poids correspondant au choix donné
    return question.getChoice().stream()
        .filter(choice -> choice.getChoice().equals(response))
        .mapToDouble(Choice::getPoids)
        .findFirst()
        .orElse(0); // Return 0 if the choice is not found
  }

  /**
   * Get the number of submitted forms
   *
   * @return the number of submitted forms
   */
  public int getNumberOfSubmittedForms() {
    return repository.findAll().stream()
        .filter(Form::isSubmitted)
        .toList()
        .size();
  }

  /**
   * Get the average ESG score of all submitted forms
   *
   * @return the average ESG score
   */
  public double getAverageScoreESG() {
    List<Form> forms = (List<Form>) repository.findAll();
    double totalScore = 0;
    for (Form form : forms) {
      totalScore += form.getScoreESG();
    }
    return totalScore / forms.size();
  }

  /**
   * Get the number of forms in progress
   *
   * @return the number of forms in progress
   */
  public int getNumberOfFormsInProgress() {
    return (int) repository.findAll().
        stream().filter(form -> !form.isSubmitted()).count();
  }

}