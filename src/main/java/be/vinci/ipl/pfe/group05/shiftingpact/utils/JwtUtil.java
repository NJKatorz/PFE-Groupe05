package be.vinci.ipl.pfe.group05.shiftingpact.utils;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.models.User;
import be.vinci.ipl.pfe.group05.shiftingpact.services.CompaniesService;
import be.vinci.ipl.pfe.group05.shiftingpact.services.UsersService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final String SECRET_KEY = "MySuperSecretKey";
  private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

  private final Algorithm jwtAlgorithm;
  private final JWTVerifier jwtVerifier;

  @Autowired
  CompaniesService companiesService;

  @Autowired
  UsersService usersService;

  public JwtUtil() {
    // Initialisation de l'algorithme HMAC256 avec une clé secrète
    this.jwtAlgorithm = Algorithm.HMAC256(SECRET_KEY);
    // Initialisation du vérificateur JWT
    this.jwtVerifier = JWT.require(jwtAlgorithm)
        .withIssuer("auth0")
        .build();

  }

  public String generateTokenForUser(User user) {
    return JWT.create()
        .withIssuer("auth0")
        .withClaim("role", "user")
        .withClaim("userId", user.getUserId())
        .withSubject(user.getEmail()) // Subject du token
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(jwtAlgorithm);
  }

  public String generateTokenForCompany(Company company) {
    return JWT.create()
        .withIssuer("auth0")
        .withClaim("role", "company")
        .withClaim("companyId", company.getCompanyId())
        .withSubject(company.getLogin()) // Subject du token
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(jwtAlgorithm);
  }


  public Object verify(String token) { // à refactore en 2 méthode une pour company et une pour user
    try {
      DecodedJWT decodedToken = jwtVerifier.verify(token);
      String role = decodedToken.getClaim("role").asString();

      if ("company".equals(role)) {
        int companyId = decodedToken.getClaim("companyId").asInt();
        return companiesService.getOneById(companyId);
      } else if ("user".equals(role)) {
        int userId = decodedToken.getClaim("userId").asInt();
        return usersService.getOneById(userId);
      } else {
        throw new JWTVerificationException("Invalid role in token");
      }
    } catch (JWTVerificationException e) {
      System.out.println("Token verification failed");
      return null;
    }
  }

}
