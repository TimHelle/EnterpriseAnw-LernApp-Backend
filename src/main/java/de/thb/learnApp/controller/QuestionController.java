package de.thb.learnApp.controller;

import de.thb.learnApp.dto.QuestionDTO;
import de.thb.learnApp.model.Category;
import de.thb.learnApp.model.Question;
import de.thb.learnApp.service.CategoryService;
import de.thb.learnApp.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;
    private final CategoryService categoryService;

    QuestionController(QuestionService questionService, CategoryService categoryService) {
        this.questionService = questionService;
        this.categoryService = categoryService;
    }

    @GetMapping("/questions")
    public @ResponseBody List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @GetMapping("/questions/{id}")
    public Question getQuestions(@PathVariable Long id) {
        return questionService.getQuestion(id);
    }

    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public Question saveQuestions(@Valid @RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        question.setText(questionDTO.getText());
        question.setExplanation(questionDTO.getExplanation());

        Category category;
        if (questionDTO.getCategory().getId() > 0) {
            category = this.categoryService.getCategory(questionDTO.getCategory().getId());
        } else {
            category = questionDTO.getCategory();
        }
        question.setCategory(category);

        question.setAnswers(questionDTO.getAnswers());
        Question result = questionService.saveQuestion(question);
        return result;
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable Long id) {
        Question question = questionService.getQuestion(id);
        question.setText(questionDTO.getText());
        question.setExplanation(questionDTO.getExplanation());
        return questionService.saveQuestion(question);
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        Question question = questionService.getQuestion(id);
        questionService.deleteQuestion(question);
    }
}
