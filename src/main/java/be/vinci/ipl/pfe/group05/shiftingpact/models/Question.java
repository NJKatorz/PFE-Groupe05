package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection= "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
  private ObjectId id;
  @NonNull
  private int questionId;
  @NonNull
  private String question;
  private List<Choice> choice;
  @NonNull
  private String enjeux;
  @NonNull
  private String category;
  @NonNull
  private String pillar;
  @NonNull
  private String template;
  @NonNull
  private String type;
}
