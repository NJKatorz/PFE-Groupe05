package be.vinci.ipl.pfe.group05.shiftingpact.controllers;


import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.services.FormsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
