package de.thb.learnApp.service;

import de.thb.learnApp.exception.QuestionNotFoundException;
import de.thb.learnApp.model.Question;
import de.thb.learnApp.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questionRepository.findAll().forEach(questions::add);
        return questions;
    }

    @Override
    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
            .orElseThrow(()-> new QuestionNotFoundException(id));
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
