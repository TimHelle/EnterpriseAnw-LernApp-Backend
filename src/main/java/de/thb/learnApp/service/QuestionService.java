package de.thb.learnApp.service;

import de.thb.learnApp.model.Question;
import java.util.List;

public interface QuestionService {

    List<Question> getQuestions();

    Question getQuestion(Long id);

    Question saveQuestion(Question question);

    void deleteQuestion(Question question);
}
