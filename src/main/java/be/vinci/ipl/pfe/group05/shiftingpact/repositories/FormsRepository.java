package be.vinci.ipl.pfe.group05.shiftingpact.repositories;

import be.vinci.ipl.pfe.group05.shiftingpact.models.Form;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormsRepository extends MongoRepository<Form, ObjectId> {

}
