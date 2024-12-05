package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.services.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
@RestController
public class CompaniesController {

  @Autowired
  CompaniesService service;
  @PostMapping("/companies/validate/{id}")
  public ResponseEntity<Void> validate(@PathVariable int id) {
    boolean created = service.validateOne(id);
    if(!created)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
