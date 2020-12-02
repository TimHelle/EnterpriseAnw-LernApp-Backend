package de.thb.learnApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @NotNull
    @NotBlank
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String text;
    private String explanation;

    //@ElementCollection
    //private List<Integer> categories = new ArrayList<>();

    //@ElementCollection
    //private List<Answer> answers = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
