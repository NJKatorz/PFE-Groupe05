package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Company;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.CompaniesRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompaniesService {
  @Autowired
  CompaniesRepository repository;

  @Autowired
  EmailService emailService;

  public Company getOneById(int id){
    return repository.findByCompanyId(id).orElse(null);
  }

  public Company login(String login, String password) {
      Company company =  repository.findByLogin(login).orElse(null);
      if (company == null) {
        return null;
      }
      if(!BCrypt.checkpw(password, company.getPassword()))
        return null;
      return company;
  }

  public boolean validateOne(int id){
    Company company = repository.findByCompanyId(id).orElse(null);
    if(company == null)
      return false;

    String generatedPassword = generateRandomPassword();

    String hashedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt());
    company.setPassword(hashedPassword);

    company.setLogin(generateLogin(company.getName(), company.getNaceCode()));
    company.setRegistrationDate(LocalDateTime.now());
    company.setValidated(true);
    //company.setScores(new ArrayList<>());
   // company.setCompanyId((int) repository.count());
    repository.save(company);

    String emailContent = "Bonjour " + company.getName() + ",\n\n" +
        "Félicitations, votre entreprise a été validée avec succès !\n\n" +
        "Voici vos identifiants de connexion :\n" +
        "Login : " + company.getLogin() + "\n" +
        "Mot de passe : " + generatedPassword + "\n\n" +
        "Veuillez changer votre mot de passe dès votre première connexion.\n\n" +
        "Cordialement,\nL'équipe";
    emailService.sendSimpleEmail(company.getContactEmail(), "Validation de votre entreprise", emailContent);
    return true;
  }


  private String generateLogin(String companyName, String codeNace) {
    // 3 premières lettres du nom de l'entreprise
    String firstThreeLetters = companyName.length() >= 3 ? companyName.substring(0, 3).toLowerCase() : companyName.toLowerCase();
    // 3 derniers chiffres du code NACE
    String lastThreeDigits = codeNace.length() >= 3 ? codeNace.substring(codeNace.length() - 3) : codeNace;
    // Combinaison des 3 premières lettres du nom et des 3 derniers chiffres du code NACE
    return firstThreeLetters + lastThreeDigits;
  }

  // Méthode pour générer un mot de passe aléatoire sécurisé
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
