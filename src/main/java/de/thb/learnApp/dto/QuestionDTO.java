package de.thb.learnApp.dto;

import de.thb.learnApp.model.Category;
import de.thb.learnApp.model.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    @ApiModelProperty(example = "A + B")
    @NotBlank
    private String text;

    @ApiModelProperty(example = "sum of", position = 2)
    @NotBlank
    private String explanation;

    private Category category;

    public String getText() {
        return text;
    }

    public String getExplanation() {
        return explanation;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}