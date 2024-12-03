package be.vinci.ipl.pfe.group05.shiftingpact.models;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Questionnaire {
  @Id
  private int id;
  private Statut statut;
  private String challenge;
  private String template;
  private List<Question> questions;

  public Questionnaire(int id, Statut statut, String challenge, String template, List<Question> questions) {
    this.id = id;
    this.statut = statut;
    this.challenge = challenge;
    this.template = template;
    this.questions = questions;
  }

  public enum Statut {
    COMPLETE("Complété"), EN_COURS("En cours"), A_COMPLETER("A compléter");

    private String statut;

    Statut(String statut) {
      this.statut = statut;
    }

    public String getStatut() {
      return statut;
    }


  }
  }



