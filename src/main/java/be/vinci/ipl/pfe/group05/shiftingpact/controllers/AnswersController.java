package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.services.AnswersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswersController {

  @Autowired
  AnswersService service;
  @PostMapping("/answers")
  public Answer saveAnswers(@RequestBody List<Answer> answers) {
    return service.saveAnswers(answers);
  }

}
