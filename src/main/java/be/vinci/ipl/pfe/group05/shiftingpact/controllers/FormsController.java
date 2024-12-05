package be.vinci.ipl.pfe.group05.shiftingpact.controllers;


import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.services.FormsService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FormsController {
  @Autowired
  FormsService service;
  @PostMapping("/forms/{companyId}")
  public Form createOne(@PathVariable int companyId) {
    return service.createOne(companyId);
  }

  @GetMapping("/forms")
  public Iterable<Form> getAllForms() {
    return service.getAllForms();
  }

}
