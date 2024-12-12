package be.vinci.ipl.pfe.group05.shiftingpact.repositories;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for the companies collection
 */
@Repository
public interface CompaniesRepository extends MongoRepository<Company, Integer> {
  /**
   * Find a company by its id
   * @param companyId the id of the company
   * @return the company
   */
  Optional<Company> findByCompanyId(int companyId);

  /**
   * Find a company by its login
   * @param login the login of the company
   * @return the company
   */
  Optional<Company> findByLogin(String login);

}
