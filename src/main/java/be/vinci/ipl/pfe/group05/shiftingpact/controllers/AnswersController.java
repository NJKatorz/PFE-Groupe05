package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.services.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswersController {

  @Autowired
  AnswersService service;

  /**
   * Save a response
   * @param answer the response to save
   * @return the response saved
   */
  @PostMapping("/answers")
  public Answer saveResponse(@RequestBody Answer answer) {
    return service.createOne(answer);
  }

}
