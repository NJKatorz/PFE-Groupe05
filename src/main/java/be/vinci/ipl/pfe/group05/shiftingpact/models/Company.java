package be.vinci.ipl.pfe.group05.shiftingpact.models;

import java.time.LocalDateTime;
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
  private int id;
  @NonNull
  private String name;
  @NonNull
  @Field("company_number")
  private String companyNumber;
  @NonNull
  @Field("legal_form")
  private String legalForm;
  @NonNull
  private String address;
  @NonNull
  @Field("nace_code")
  private String naceCode;
  @NonNull
  @Field("registration_date")
  private LocalDateTime registrationDate;
}
