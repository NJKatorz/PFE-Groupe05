package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.models.User;
import be.vinci.ipl.pfe.group05.shiftingpact.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UsersController {

  @Autowired
  UsersService usersService;

  @GetMapping("/users/count")
  public int getNumberOfRegisteredUsers() {
    return usersService.getNumberOfRegisteredUsers();
  }

}
