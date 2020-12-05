package de.thb.learnApp.repository;

import de.thb.learnApp.model.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Answer.class, idClass = Long.class)
public interface AnswerRepository extends CrudRepository<Answer, Long> {

}

