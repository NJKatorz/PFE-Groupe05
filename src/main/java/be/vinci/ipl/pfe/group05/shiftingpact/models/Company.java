package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
  @Id
  private String id;

  @NonNull
  private int companyId;

  @NonNull
  private String name;
  @NonNull
  private String companyNumber;
  @NonNull
  private String legalForm;
  @NonNull
  private String address;
  @NonNull
  private String naceCode;

  @NonNull
  private LocalDateTime registrationDate;

  @NonNull
  private List<String> templates;
  //private List<Integer> scores;

  @NonNull
  private String login;

  @NonNull
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @NonNull
  private boolean isValidated;

  @NonNull
  private String contactEmail;

  @NonNull
  private int numberOfWorkers;
  @NonNull
  private boolean sellsProduct;
  @NonNull
  private boolean isOwner;
  //champs a un formulaire en cours
}

//TODO