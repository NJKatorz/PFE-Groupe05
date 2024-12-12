package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
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
  private ObjectId id;
  @NonNull
  private int userId;
  @NonNull
  private String email;
  @NonNull
  @Field("last_name")
  private String lastName;
  @NonNull
  @Field("first_name")
  private String firstName;
  @NonNull
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;
}
