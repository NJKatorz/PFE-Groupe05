package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.User;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
  @Autowired
  UsersRepository repository;

  public User getOneById(int id){
    return  repository.findById(id).orElse(null);
  }
}
