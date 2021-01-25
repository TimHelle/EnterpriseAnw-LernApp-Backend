package de.thb.learnApp.dto;

import de.thb.learnApp.model.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @ApiModelProperty(example = "Category Math", position = 2)
    @NotBlank
    private String description;

    @ApiModelProperty(example = "Math")
    @NotBlank
    private String title;

    @NotBlank
    private String hash;

    private List<Question> questions = new ArrayList<>();

    public String getHash() {
        return hash;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
