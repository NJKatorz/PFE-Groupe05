package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.FormsRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    return repository.save(form);
  }

  public Iterable<Form> getAllForms() {
    return repository.findAll();
  }
}
