package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.services.CompaniesService;
import be.vinci.ipl.pfe.group05.shiftingpact.services.FormsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CompaniesController {

    @Autowired
    CompaniesService service;

    @GetMapping("/companies/{companyId}")
    public Company getOneById(@PathVariable int companyId){
        return service.getOneById(companyId);
    }

  }

