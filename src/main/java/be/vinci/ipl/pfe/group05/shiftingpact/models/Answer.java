package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection= "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
  @Id
  private ObjectId id;
  @NonNull
  @Field("answer_id")
  private int answerId;
  @NonNull
  @Field("form_id")
  private Integer formId;
  @NonNull
  @Field("question_id")
  private Integer questionId;
  private String response;
  @Field("response_in_two_years")
  private String responseInTwoYears;
  private String comments;

  public boolean invalid(){
    return answerId<=0 || formId<=0 || questionId<=0;
  }
}
