package de.thb.learnApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class Category {
    @Id
    @NotNull
    @NotBlank
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "question_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
