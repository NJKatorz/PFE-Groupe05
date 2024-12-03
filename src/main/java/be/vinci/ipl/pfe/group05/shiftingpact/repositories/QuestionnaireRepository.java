package be.vinci.ipl.pfe.group05.shiftingpact.repositories;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Questionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionnaireRepository extends MongoRepository<Questionnaire, Integer> {

}
