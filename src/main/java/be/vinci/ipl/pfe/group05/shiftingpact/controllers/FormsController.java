package be.vinci.ipl.pfe.group05.shiftingpact.controllers;


import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.services.FormsService;
import java.util.List;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FormsController {
  @Autowired
  FormsService service;

  @GetMapping("/forms/{companyId}/progress")
  public List<Form> getAllFormsInProgress(@PathVariable Integer companyId) {
    return service.getAllFormsInProgress(companyId);
  }
  @PostMapping("/forms/{companyId}")
  public Form createOne(@PathVariable int companyId) {
    return service.createOne(companyId);
  }

  @GetMapping("/forms")
  public Iterable<Form> getAllForms() {
    return service.getAllForms();
  }

  @GetMapping("/forms/{formId}")
  public Form getOneById(@PathVariable int formId) {
    return service.getOneFormById(formId);
  }

  @PostMapping("/forms/{formId}/saveAnswers")
  public Form saveAnswers(@PathVariable int formId, @RequestBody List<Answer> answers) {
    if(answers == null || answers.isEmpty()) {
      throw new IllegalArgumentException("Les réponses ne peuvent pas être vides");
    }
    return service.saveAnswers(formId, answers);
  }

  @PostMapping("/forms/{formId}/submit")
  public Form submit(@PathVariable int formId) {return service.submit(formId);}
}
