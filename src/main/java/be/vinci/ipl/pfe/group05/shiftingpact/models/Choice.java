package be.vinci.ipl.pfe.group05.shiftingpact.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model for the choices collection
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
  private String choice;
  private int poids;
}