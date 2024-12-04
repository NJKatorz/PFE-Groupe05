package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection= "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
  @Field("question_id")
  private ObjectId id;

  @Field("question_id")
  private int questionId;
  @NonNull
  @Field("company_id")
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
