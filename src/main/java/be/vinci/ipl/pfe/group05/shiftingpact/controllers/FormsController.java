package be.vinci.ipl.pfe.group05.shiftingpact.controllers;


import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.services.FormsService;
import java.util.List;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  /**
   * Get all forms in progress
   *
   * @param companyId the id of the company
   * @return a list of all forms in progress
   */
  @GetMapping("/forms/company/{companyId}/progression")
  public List<Form> getAllFormsInProgress(@PathVariable Integer companyId) {
    return service.getAllFormsInProgress(companyId);
  }

  /**
   * Create a form
   *
   * @param companyId the id of the company
   * @return the form created
   */
  @PostMapping("/forms/company/{companyId}")
  public Form createOne(@PathVariable int companyId) {
    return service.createOne(companyId);
  }

  /**
   * Get all forms
   *
   * @return a list of all forms
   */
  @GetMapping("/forms")
  public Iterable<Form> getAllForms() {
    return service.getAllForms();
  }

  /**
   * Get a form by its id
   *
   * @param formId the id of the form
   * @return the form with the given id
   */
  @GetMapping("/forms/{formId}")
  public Form getOneById(@PathVariable int formId) {
    return service.getOneFormById(formId);
  }

  /**
   * Save answers
   *
   * @param formId  the id of the form
   * @param answers the answers to save
   * @return the form with the answers saved
   */
  @PostMapping("/forms/{formId}/saveAnswers")
  public ResponseEntity<?> saveAnswers(@PathVariable int formId,
      @RequestBody List<Answer> answers) {
    try {
      if (answers == null || answers.isEmpty()) {
        throw new IllegalArgumentException("Les réponses ne peuvent pas être vides");
      }

      Form form = service.saveAnswers(formId, answers);
      return ResponseEntity.ok(form);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Une erreur interne s'est produite");
    }
  }

  /**
   * Submit a form and calculate the score
   *
   * @param formId the id of the form
   * @return the form submitted
   */
  @PostMapping("/forms/{formId}/submit")
  public ResponseEntity<?> submit(@PathVariable int formId) {
    try {
      Form form = service.submit(formId);
      return ResponseEntity.ok(form);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Une erreur interne s'est produite");
    }
  }

  /**
   * Get a form by its company id
   *
   * @param companyId the id of the company
   * @return the form with the given company id
   */
  @GetMapping("/forms/company/{companyId}")
  public Form getOneByCompanyId(@PathVariable int companyId) {
    return service.getFormByCompanyId(companyId);
  }

  /**
   * Get the number of submitted forms
   *
   * @return the number of submitted forms
   */
  @GetMapping("/forms/formsSubmitted")
  public int getFormsSubmitted() {
    return service.getNumberOfSubmittedForms();
  }

  /**
   * Get the average score ESG of all forms
   *
   * @return the average score ESG
   */
  @GetMapping("/forms/averageScoreESG")
  public double getAverageScoreESG() {
    return service.getAverageScoreESG();
  }

  /**
   * Get the number of forms in progress
   *
   * @return the number of forms in progress
   */
  @GetMapping("/forms/formsInProgress")
  public int getFormsInProgress() {
    return service.getNumberOfFormsInProgress();
  }
}
