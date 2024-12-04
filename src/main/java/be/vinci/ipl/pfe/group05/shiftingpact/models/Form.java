package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection= "forms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Form {
  @Id
  private int id;
  @NonNull
  @Field("company_id")
  private int companyID;
  @NonNull
  private int total;
  @NonNull
  private int completed;
  @NonNull
  @Field("created_at")
  private LocalDateTime createdAt;
  @NonNull
  @Field("send_at")
  private LocalDateTime sendAt;
  @NonNull
  private String template;
  @NonNull
  @Field("question_list")
  private List<Question> questionList;


  }



