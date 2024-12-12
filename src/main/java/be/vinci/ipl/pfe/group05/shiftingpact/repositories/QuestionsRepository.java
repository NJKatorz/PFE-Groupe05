package be.vinci.ipl.pfe.group05.shiftingpact.repositories;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the questions collection
 */
@Repository
public interface QuestionsRepository extends MongoRepository<Question, Integer> {
  /**
   * Get all questions by template
   * @param template the template of the questions
   * @return the list of questions with the template
   */
  List<Question> findByTemplate(String template);
}
