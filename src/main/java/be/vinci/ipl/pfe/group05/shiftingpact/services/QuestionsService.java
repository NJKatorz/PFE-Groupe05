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

  /**
   * Get all questions by template
   *
   * @param template the template of the questions
   * @return a list of all questions with the given template
   */
  public List<Question> getAllByTemplate(String template) {
    return repository.findByTemplate(template);
  }

  /**
   * Get all questions
   *
   * @return a list of all questions
   */
  public List<Question> getAll() {
    return repository.findAll();
  }
}
