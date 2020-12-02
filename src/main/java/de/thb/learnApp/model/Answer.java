package de.thb.learnApp.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class Answer {
    @Id
    @NotNull
    @NotBlank
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;
    private boolean isCorrect;
}
