package be.vinci.ipl.pfe.group05.shiftingpact;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Questionnaire;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.QuestionnaireRepository;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShiftingPactApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShiftingPactApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(QuestionnaireRepository repository) {
    return args -> {
      Questionnaire questionnaire = new Questionnaire(
          1,
          Questionnaire.Statut.A_COMPLETER,
          "Challenge 1",
          "Template 1",
          new ArrayList<>()
      );
      repository.insert(questionnaire);
    };
  }

}
