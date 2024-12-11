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

  public User getOneById(int id){
    return repository.findByUserId(id).orElse(null);
  }

  public User login(String email, String password) {
    User user = repository.findByEmail(email).orElse(null);
    if(user == null)
      return null;
    if(!BCrypt.checkpw(password, user.getPassword()))
      return null;
    return user;
  }

  public int getNumberOfRegisteredUsers() {
    return (int) repository.count();
  }
}
