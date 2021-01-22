package de.thb.learnApp.dto;

import de.thb.learnApp.model.Question;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    @NotBlank
    private String text;
    @NotNull
    private boolean isCorrect;

    public String getText() {
        return text;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }
}
