package be.vinci.ipl.pfe.group05.shiftingpact.repositories;

import be.vinci.ipl.pfe.group05.shiftingpact.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<User, Integer> {

}