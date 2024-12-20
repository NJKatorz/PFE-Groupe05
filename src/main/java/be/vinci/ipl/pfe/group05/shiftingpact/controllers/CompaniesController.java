package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.services.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CompaniesController {

  @Autowired
  CompaniesService service;

  /**
   * Get a company by its id
   *
   * @param companyId the id of the company
   * @return the company with the given id
   */
  @GetMapping("/companies/{companyId}")
  public Company getOneById(@PathVariable int companyId) {
    return service.getOneById(companyId);
  }

  /**
   * Validate a company
   *
   * @param id the id of the company to validate
   * @return a response entity with status 201 if the company is validated, 404 otherwise
   */
  @PostMapping("/companies/{id}/validate")
  public ResponseEntity<Void> validate(@PathVariable int id) {
    boolean created = service.validateOne(id);
    if (!created) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
  }

  /**
   * Get all companies
   *
   * @return all companies
   */
  @GetMapping("/companies")
  public Iterable<Company> getAll() {
    return service.getAll();
  }
}

