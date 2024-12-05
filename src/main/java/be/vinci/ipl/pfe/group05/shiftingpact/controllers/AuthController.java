package be.vinci.ipl.pfe.group05.shiftingpact.controllers;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.services.CompaniesService;
import be.vinci.ipl.pfe.group05.shiftingpact.utils.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired
  CompaniesService companiesService;
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Autowired
  JwtUtil jwtUtil;

  @PostMapping("/auth/login")
  public ResponseEntity<?> login(@RequestBody JsonNode json) {
    String login = json.get("login").asText();
    String password =  json.get("password").asText();

    Company company = companiesService.login(login, password);
    if (company !=null) {
      String token = jwtUtil.generateToken(company);
      return ResponseEntity.ok(jsonMapper.createObjectNode()
          .put("token", token)
          .putPOJO("company", company));
    } else {
      return ResponseEntity.status(401).body("Invalid username or password");
    }
  }


}
