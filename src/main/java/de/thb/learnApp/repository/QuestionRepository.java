package de.thb.learnApp.repository;

import de.thb.learnApp.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
