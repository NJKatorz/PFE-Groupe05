package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.FormsRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  QuestionsService questionsService;

  public Form createOne(Integer companyId){
    Form form = new Form();
    form.setCompanyId(companyId);
    Company company = companiesService.getOneById(companyId);
    List<String> companyTemplatesTemplate = company.getTemplates();
    List<Question> questionList = new ArrayList<>();

    for (String temp : companyTemplatesTemplate){
      List<Question> list = questionsService.getAllByTemplate(temp);
      questionList.addAll(list);
    }

    form.setFormId((int) repository.count()+1);
    form.setQuestionList(questionList);
    form.setAnswersList(new ArrayList<>());
    form.setTotal(questionList.size());
    form.setCompleted(0);
    form.setCreatedAt(LocalDateTime.now());
    form.setProgression(0);
    return repository.save(form);
  }

  public List<Form> getAllFormsInProgress(Integer companyId) {
    List<Form> allFormsInProgress = repository.findByCompanyId(companyId);

    for (Form formsInProgress : allFormsInProgress) {
      int totalQuestions = formsInProgress.getTotal();
      int completed = formsInProgress.getCompleted();

      int progression = completed/totalQuestions*100;
      formsInProgress.setProgression(progression);
    }
    return allFormsInProgress;
  }

  public Form saveAnswers(int formId, List<Answer> answers) {
  Form form = repository.findByFormId(formId).orElse(null);
  if (form == null) {
    throw new IllegalArgumentException("Formulaire introuvable");
  }

  List<Answer> existingAnswers = form.getAnswersList();
  for (Answer newAnswer : answers) {
    if (newAnswer.getResponse() != null && !newAnswer.getResponse().isEmpty()) {
      existingAnswers.removeIf(existingAnswer -> existingAnswer.getQuestionId() == newAnswer.getQuestionId() || existingAnswer.getResponse()==null || existingAnswer.getResponse().isEmpty());
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
    form.setSendAt(LocalDateTime.now());
    if (form.getCompleted() != form.getTotal()) {
      throw new IllegalArgumentException("Le formulaire n'est pas complet");
    }
    form.setCompleted(form.getTotal());
    return repository.save(form);
  }
}
