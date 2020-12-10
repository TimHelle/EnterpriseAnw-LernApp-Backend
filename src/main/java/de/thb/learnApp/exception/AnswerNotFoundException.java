package de.thb.learnApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException(Long id) {
        super("Could not find an answer " + id);
    }
}
