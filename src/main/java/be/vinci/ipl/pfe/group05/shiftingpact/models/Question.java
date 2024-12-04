package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
  @Id
  private int id;
  @NonNull
  private int companyID;
  @NonNull
  private String question;
  private List<String> choice;
  @NonNull
  private String enjeux;
  @NonNull
  private String category;
  @NonNull
  private String template;
  @NonNull
  private String type; //radio, check box, champ libre


}
