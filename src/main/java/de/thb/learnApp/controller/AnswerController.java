package de.thb.learnApp.controller;

import de.thb.learnApp.dto.AnswerDTO;
import de.thb.learnApp.model.Answer;
import de.thb.learnApp.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/answers")
    public @ResponseBody List<Answer> getAnswers() {
        return answerService.getAnswers();
    }

    @GetMapping("/answers/{id}")
    public Answer getAnswers(@PathVariable Long id) {
        return answerService.getAnswers(id);
    }

    @PostMapping("/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public Answer saveAnswers(@RequestBody @Valid AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setText(answerDTO.getText());
        answer.setCorrect(answerDTO.getIsCorrect());
        return answerService.saveAnswers(answer);
    }

    @PutMapping("/answers/{id}")
    public Answer updateAnswer(@RequestBody AnswerDTO answerDTO, @PathVariable Long id) {
        Answer answer = answerService.getAnswers(id);
        answer.setText(answerDTO.getText());
        answer.setCorrect(answerDTO.getIsCorrect());
        return answerService.saveAnswers(answer);
    }

    @DeleteMapping("/answers/{id}")
    public void deleteAnswer(@PathVariable Long id) {
        Answer answer = answerService.getAnswers(id);
        answerService.deleteAnswers(answer);
    }
}
