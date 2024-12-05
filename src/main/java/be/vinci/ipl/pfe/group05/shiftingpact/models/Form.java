package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection= "forms")
public class Form {
  @Id
  private ObjectId id;
  private int formId;
  @NonNull
  @Field("company_id")
  private Integer companyId;

  @NonNull
  private int total;
  @NonNull
  private int completed;
  @NonNull
  @Field("created_at")
  private LocalDateTime createdAt;
  @Field("send_at")
  private LocalDateTime sendAt;
  //@NonNull
  //private String template;

  @NonNull
  @Field("question_list")
  private List<Question> questionList;
  @NonNull
  @Field("answer_list")
  private List<Answer> answersList;

  @NonNull
  private double progression;


  }



