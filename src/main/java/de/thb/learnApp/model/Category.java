package de.thb.learnApp.model;

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
}
