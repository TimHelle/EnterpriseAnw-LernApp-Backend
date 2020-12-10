package de.thb.learnApp.service;

import de.thb.learnApp.model.Answer;
import java.util.List;

public interface AnswerService {

    List<Answer> getAnswers();

    Answer getAnswers(Long id);

    Answer saveAnswers(Answer answer);

    void deleteAnswers(Answer answer);
}
