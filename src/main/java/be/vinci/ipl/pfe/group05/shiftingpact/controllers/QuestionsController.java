package be.vinci.ipl.pfe.group05.shiftingpact.controllers;


import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import be.vinci.ipl.pfe.group05.shiftingpact.services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class QuestionsController {
  @Autowired
  QuestionsService service;

  /**
   * Get all questions
   *
   * @param template the template of the questions
   * @return a list of all questions with the given template
   */
  @GetMapping("/questions")
  public Iterable<Question> getAllByTemplate(@RequestParam(required = false) String template ) {
    if (template != null && !template.isEmpty()) return service.getAllByTemplate(template);
    return service.getAll();
  }
}
