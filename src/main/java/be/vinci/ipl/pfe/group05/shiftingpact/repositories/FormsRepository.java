package be.vinci.ipl.pfe.group05.shiftingpact.repositories;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the forms collection
 */
@Repository
public interface FormsRepository extends MongoRepository<Form, Integer> {
  /**
   * Find a form by its id
   * @param formId the id of the form
   * @return the form
   */
  Optional<Form> findByFormId(int formId);

  /**
   * Find the list of all forms by its company id
   * @param companyId the id of the company
   * @return the list of forms of the company
   */
  List<Form> findByCompanyId(int companyId);


}
