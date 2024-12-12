package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.CompaniesRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompaniesService {

  @Autowired
  CompaniesRepository repository;

  @Autowired
  EmailService emailService;

  /**
   * Get a company by its id
   *
   * @param id the id of the company
   * @return the associated company
   */
  public Company getOneById(int id) {
    return repository.findByCompanyId(id).orElse(null);
  }

  /**
   * Get all companies
   *
   * @return all companies
   */
  public Iterable<Company> getAll() {
    return repository.findAll();
  }

  /**
   * Login a company with its login and password If the login and password are correct, the company
   * is returned
   *
   * @param login    the login of the company
   * @param password the password of the company
   * @return the company if the login and password are correct, null otherwise
   */
  public Company login(String login, String password) {
    Company company = repository.findByLogin(login).orElse(null);
    if (company == null) {
      return null;
    }
    if (!BCrypt.checkpw(password, company.getPassword())) {
      return null;
    }
    return company;
  }

  /**
   * Validate a company
   *
   * @param id the id of the company to validate
   * @return true if the company has been validated, false otherwise
   */
  public boolean validateOne(int id) {
    Company company = repository.findByCompanyId(id).orElse(null);
    if (company == null) {
      return false;
    }

    String generatedPassword = generateRandomPassword();

    String hashedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt());
    company.setPassword(hashedPassword);

    company.setLogin(generateLogin(company.getName(), company.getNaceCode()));
    company.setRegistrationDate(LocalDateTime.now());
    company.setValidated(true);
    repository.save(company);

    String emailContent = "Bonjour " + company.getName() + ",\n\n" +
        "Félicitations, votre entreprise a été validée avec succès !\n\n" +
        "Voici vos identifiants de connexion :\n" +
        "Login : " + company.getLogin() + "\n" +
        "Mot de passe : " + generatedPassword + "\n\n" +
        "Veuillez changer votre mot de passe dès votre première connexion.\n\n" +
        "Cordialement,\nL'équipe ShiftingPact";
    emailService.sendSimpleEmail(company.getContactEmail(), "Validation de votre entreprise",
        emailContent);
    return true;
  }

  /**
   * Generate a login for a company based on its name and NACE code The login is composed of the
   * first 3 letters of the name and the last 3 digits of the NACE code
   *
   * @param companyName the name of the company to generate the login
   * @param codeNace    the NACE code of the company to generate the login
   * @return the generated login
   */
  private String generateLogin(String companyName, String codeNace) {
    // 3 premières lettres du nom de l'entreprise
    String firstThreeLetters = companyName.length() >= 3 ? companyName.substring(0, 3).toLowerCase()
        : companyName.toLowerCase();
    // 3 last digits of the NACE code
    String lastThreeDigits =
        codeNace.length() >= 3 ? codeNace.substring(codeNace.length() - 3) : codeNace;
    // Combination of the first 3 letters of the name and the last 3 digits of the NACE code
    return firstThreeLetters + lastThreeDigits;
  }

  /**
   * Generate a random secure password of 12 characters
   *
   * @return the generated password
   */
  private String generateRandomPassword() {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";
    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder();
    for (int i = 0; i < 12; i++) {
      int index = random.nextInt(chars.length());
      password.append(chars.charAt(index));
    }
    return password.toString();
  }
}
