package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
  @Id
  private ObjectId id;
  @NonNull
  private int questionId;
  @NonNull
  private int formId;
  private String response;
  private String responseInTwoYears;
  private String comments;
}
