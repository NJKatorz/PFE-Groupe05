package be.vinci.ipl.pfe.group05.shiftingpact.models;

import java.util.List;
import lombok.Data;

@Data
public class Question {
  private int id;
  private String question;
  private List<Answer> answers;

}
