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
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection= "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

  @Id
  private String id;
  @NonNull
 // @Field("company_id")
  private int companyId;
  @NonNull
  private String name;
  @NonNull
  //@Field("company_number")
  private String companyNumber;
  @NonNull
//  @Field("legal_form")
  private String legalForm;
  @NonNull
  private String address;
  @NonNull
 // @Field("nace_code")
  private String naceCode;
  @NonNull
//  @Field("registration_date")
  private LocalDateTime registrationDate;

  @NonNull
  private List<String> templates;
  private List<Integer> scores;

  @NonNull
  private String login;

  @NonNull
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @NonNull
 // @Field("is_validated")
  private boolean isValidated;

  @NonNull
  //@Field("contact_email")
  private String contactEmail;

}
