package de.thb.learnApp.controller;

import de.thb.learnApp.dto.CategoryDTO;
import de.thb.learnApp.model.Category;
import de.thb.learnApp.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public @ResponseBody
    List<Category> getCategory() {
        return categoryService.getCategory();
    }

    @GetMapping("/categories/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        category.setQuestions(categoryDTO.getQuestions()); // TODO !
        return categoryService.saveCategory(category);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        category.setQuestions(categoryDTO.getQuestions());
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        categoryService.deleteCategory(category);
    }
}
