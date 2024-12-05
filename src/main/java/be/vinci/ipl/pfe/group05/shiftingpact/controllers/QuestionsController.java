package be.vinci.ipl.pfe.group05.shiftingpact.controllers;


import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import be.vinci.ipl.pfe.group05.shiftingpact.services.QuestionsService;
import be.vinci.ipl.pfe.group05.shiftingpact.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@RestController
public class QuestionsController {
  @Autowired
  QuestionsService service;

  @Autowired
  JwtUtil jwtUtil;

  @GetMapping("/questions")
  public Iterable<Question> getAllByTemplate(@RequestParam(required = false) String template, @RequestHeader("Authorization") String token ) {
   // if (jwtUtil.verify(token) == null) {
     // System.out.println("error");
      //return null;
    //}
    if (template != null && !template.isEmpty()) return service.getAllByTemplate(template);
    return service.getAll();
  }
}
