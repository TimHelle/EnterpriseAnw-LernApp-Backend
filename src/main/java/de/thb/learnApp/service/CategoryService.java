package de.thb.learnApp.service;

import de.thb.learnApp.model.Category;
import de.thb.learnApp.model.Question;

import java.util.List;

public interface CategoryService {

    Category getCategory(Long id);

    List<Category> getCategory();

    Category saveCategory(Category category);

    void deleteCategory(Category category);
}

