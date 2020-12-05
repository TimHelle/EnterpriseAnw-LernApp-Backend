package de.thb.learnApp.controller;

import de.thb.learnApp.dto.questionDTO;
import de.thb.learnApp.model.Question;
import de.thb.learnApp.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    QuestionController(QuestionService questionService) {
        this.questionService = questionService;
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
    public Question saveQuestions(@Valid @RequestBody questionDTO questionDTO) {
        Question question = new Question();
        question.setText(questionDTO.getText());
        question.setExplanation(questionDTO.getExplanation());
        return questionService.saveQuestion(question);
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@RequestBody questionDTO questionDTO, @PathVariable Long id) {
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
