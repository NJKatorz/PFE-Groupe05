package be.vinci.ipl.pfe.group05.shiftingpact.controllers;


import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.services.FormsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormsController {
  @Autowired
  FormsService service;
  @PostMapping("/forms/{companyId}")
  public Form createOne(@PathVariable int companyId) {
    return service.createOne(companyId);
  }

  @PostMapping("/forms/{formId}/submit")
  public Form submit(@PathVariable int formId) {return service.submit(formId);}
}
