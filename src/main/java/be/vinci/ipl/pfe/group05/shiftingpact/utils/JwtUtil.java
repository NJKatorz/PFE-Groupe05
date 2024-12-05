package be.vinci.ipl.pfe.group05.shiftingpact.utils;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final String SECRET_KEY = "MySuperSecretKey";
  private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 heures

  private final Algorithm jwtAlgorithm;
  private final JWTVerifier jwtVerifier;

  public JwtUtil() {
    // Initialisation de l'algorithme HMAC256 avec une clé secrète
    this.jwtAlgorithm = Algorithm.HMAC256(SECRET_KEY);

    // Initialisation du vérificateur JWT
    this.jwtVerifier = JWT.require(jwtAlgorithm).build();
  }

  // Génération d'un token JWT
  public String generateToken(Company company) {
    return JWT.create()
        .withIssuer("auth0")
        .withClaim("company", company.getCompanyId())
        .withSubject(company.getLogin()) // Subject du token (exemple : login ou ID)
        .withIssuedAt(new Date()) // Date de génération du token
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration
        .sign(jwtAlgorithm); // Signature du token avec l'algorithme
  }

  // Extraction du sujet (par exemple, le login) depuis le token
  public String extractLogin(String token) {
    DecodedJWT decodedJWT = jwtVerifier.verify(token); // Validation et décryptage du token
    return decodedJWT.getSubject(); // Extraction du champ "subject"
  }

  // Validation d'un token
  public boolean validateToken(String token, String login) {
    try {
      String extractedLogin = extractLogin(token); // Extraction du login
      return extractedLogin.equals(login); // Vérification de l'utilisateur
    } catch (Exception e) {
      return false; // En cas d'erreur (token invalide ou expiré)
    }
  }
}
