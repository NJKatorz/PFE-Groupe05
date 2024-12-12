package be.vinci.ipl.pfe.group05.shiftingpact.repositories;

import be.vinci.ipl.pfe.group05.shiftingpact.models.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the users collection
 */
@Repository
public interface UsersRepository extends MongoRepository<User, Integer> {
  /**
   * Find a user by its id
   * @param userId the id of the user
   * @return the user
   */
  Optional<User> findByUserId(int userId);
  
  /**
   * Find a user by its email
   * @param email the email of the user
   * @return the user
   */
  Optional<User> findByEmail(String email);
}
