package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  private Integer companyId;
  @NonNull
  private int total;
  @NonNull
  private int completed;
  @NonNull
  private LocalDateTime createdAt;
  private LocalDateTime sendAt;

  @NonNull
  private List<Question> questionList;
  @NonNull
  private List<Answer> answersList;

  @NonNull
  private List<Question> otherQuestions;

  @NonNull
  private double progression;
  @NonNull
  private boolean isSubmitted;

  private double scoreE;

  private double scoreS;

  private double scoreG;

  private double scoreESG;

  }

