package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.models.User;
import be.vinci.ipl.pfe.group05.shiftingpact.services.CompaniesService;
import be.vinci.ipl.pfe.group05.shiftingpact.services.UsersService;
import be.vinci.ipl.pfe.group05.shiftingpact.utils.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class AuthController {

  @Autowired
  CompaniesService companiesService;

  @Autowired
  UsersService usersService;
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Autowired
  JwtUtil jwtUtil;

  /**
   * Login a user or a company with its login/email and password If the login/email and password are
   * correct, the user/company is returned
   *
   * @param json the login/email and password of the user/company
   * @return the user/company if the login/email and password are correct, null otherwise
   */
  @PostMapping("/auth/login")
  public ResponseEntity<?> login(@RequestBody JsonNode json) {
    String login = json.has("login") ? json.get("login").asText(null) : null;
    String email = json.has("email") ? json.get("email").asText(null) : null;
    String password = json.has("password") ? json.get("password").asText(null) : null;

    if ((login == null && email == null) || password == null) {
      return ResponseEntity.badRequest().body("Both login/email and password are required.");
    }

    // Authentication as a company
    if (login != null) {
      Company company = companiesService.login(login, password);
      if (company != null) {
        String token = jwtUtil.generateTokenForCompany(company);
        return ResponseEntity.ok(jsonMapper.createObjectNode()
            .put("token", token)
            .put("role", "company")
            .putPOJO("company", company));
      }
      else {
        return ResponseEntity.status(401).body("Invalid login or password for company.");
      }
    }

    // Authentication as an admin user
     if (email != null) {
      User user = usersService.login(email, password);
      if (user != null) {
        String token = jwtUtil.generateTokenForUser(user);
        return ResponseEntity.ok(jsonMapper.createObjectNode()
            .put("token", token)
            .put("role", "admin")
            .putPOJO("user", user));
      } else {
        return ResponseEntity.status(401).body("Invalid email or password for admin.");
      }
      }
    return ResponseEntity.status(500).body("Unexpected error during authentication.");
  }

}
