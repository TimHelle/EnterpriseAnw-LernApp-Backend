package de.thb.learnApp.repository;

import de.thb.learnApp.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Category.class, idClass = Long.class)
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
