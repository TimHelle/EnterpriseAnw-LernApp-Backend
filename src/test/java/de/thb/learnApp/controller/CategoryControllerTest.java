package de.thb.learnApp.controller;

import de.thb.learnApp.model.Answer;
import de.thb.learnApp.model.Category;
import de.thb.learnApp.model.Question;
import de.thb.learnApp.service.CategoryService;
import de.thb.learnApp.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testGetCategory() throws Exception {
        List<Category> categories = new ArrayList<>();
        Category c = new Category();
        c.setTitle("Math");
        c.setDescription("Addition");
        Question q1 = new Question();
        q1.setText("A+B");
        q1.setExplanation("Test");
        Answer a1 = new Answer();
        a1.setText("A+B");
        Answer a2 = new Answer();
        a2.setText("AB");
        a1.setCorrect(true);
        a2.setCorrect(false);
        a1.setQuestion(q1);
        a2.setQuestion(q1);
        List<Answer> answers = q1.getAnswers();
        q1.setAnswers(answers);
        c.setQuestion(q1);

        categories.add(c);


        when(categoryService.getCategory()).thenReturn(categories);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("[{\"id\":0,\"description\":\"Addition\",\"title\":\"Math\"}]"))
                );
    }
}