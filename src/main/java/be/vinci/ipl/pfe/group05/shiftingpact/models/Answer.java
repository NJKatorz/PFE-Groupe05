package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
  //@Id
  //private ObjectId id;
  @NonNull
  private int questionId;
  private String response;
  private String comments;
}
