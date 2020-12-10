package de.thb.learnApp.service;


import de.thb.learnApp.exception.AnswerNotFoundException;
import de.thb.learnApp.model.Answer;
import de.thb.learnApp.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> getAnswers() {
        List<Answer> answers = new ArrayList<>();
        answerRepository.findAll().forEach(answers::add);
        return answers;
    }

    @Override
    public Answer getAnswers(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(()-> new AnswerNotFoundException(id));
    }

    @Override
    public Answer saveAnswers(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswers(Answer question) {
        answerRepository.delete(question);
    }
}
