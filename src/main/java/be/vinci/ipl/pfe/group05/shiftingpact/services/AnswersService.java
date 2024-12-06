package be.vinci.ipl.pfe.group05.shiftingpact.services;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Answer;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import be.vinci.ipl.pfe.group05.shiftingpact.models.Question;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.AnswersRepository;
import be.vinci.ipl.pfe.group05.shiftingpact.repositories.FormsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswersService {

  @Autowired
  AnswersRepository repository;
  @Autowired
  FormsRepository formsRepository;
 public Answer createOne(Answer answer){
    return repository.save(answer);
  }

}
