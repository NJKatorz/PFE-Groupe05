package be.vinci.ipl.pfe.group05.shiftingpact.models;

import com.mongodb.lang.NonNull;
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


@Document(collection= "forms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Form {
  @Id
  private int id;
  @NonNull
  private int companyID;
  @NonNull
  private int total;
  @NonNull
  private int completed;
  @NonNull
  private LocalDateTime createdAt;
  @NonNull
  private LocalDateTime sendAt;
  @NonNull
  private String template;
  @NonNull
  private List<Question> questionList;


  }



