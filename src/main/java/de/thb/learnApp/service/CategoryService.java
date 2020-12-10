package de.thb.learnApp.service;

import de.thb.learnApp.model.Category;

public interface CategoryService {

    Category getCategory(Long id);

    Category saveCategory(Category question);

    void deleteCategory(Category question);
}

