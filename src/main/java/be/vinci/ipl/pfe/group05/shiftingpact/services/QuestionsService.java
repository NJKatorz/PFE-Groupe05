package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.QuestionsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionsService {
  @Autowired
  QuestionsRepository repository;

  public List<Question> getAllByTemplate(String template) {
    return repository.findByTemplate(template);
  }

  public List<Question> getAll(){
    return repository.findAll();
  }
}
