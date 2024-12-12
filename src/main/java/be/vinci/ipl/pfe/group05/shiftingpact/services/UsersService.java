package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.User;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  @Autowired
  UsersRepository repository;

  /**
   * Get a user by its id
   *
   * @param id the id of the user
   * @return the associated user
   */
  public User getOneById(int id) {
    return repository.findByUserId(id).orElse(null);
  }

  /**
   * Login a user with its email and password If the email and password are correct, the user is
   * returned
   *
   * @param email    the email of the user
   * @param password the password of the user
   * @return the user if the email and password are correct, null otherwise
   */
  public User login(String email, String password) {
    User user = repository.findByEmail(email).orElse(null);
    if (user == null) {
      return null;
    }
    if (!BCrypt.checkpw(password, user.getPassword())) {
      return null;
    }
    return user;
  }

}
