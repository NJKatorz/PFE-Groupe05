package be.vinci.ipl.pfe.group05.shiftingpact.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection= "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  private int id;

  @Field("company_id")
  private int companyId;

  @NonNull
  private String email;

  @NonNull
  @Field("last_name")
  private String lastName;

  @NonNull
  @Field("first_name")
  private String firstName;

  private String fonction;

  @NonNull
  @Field("is_admin")
  private boolean isAdmin;
}
