package de.thb.learnApp.controller;

import de.thb.learnApp.model.Question;
import de.thb.learnApp.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Test
    public void testGetQuestions() throws Exception {
        List<Question> questions = new ArrayList<>();

        Question q1 = new Question();
        q1.setText("A+B");
        q1.setExplanation("Test");

        questions.add(q1);

        when(questionService.getQuestions()).thenReturn(questions);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/questions"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("[{\"id\":0,\"text\":\"A+B\",\"explanation\":\"Test\"}]"))
                );
    }

    @Test
    public void testCreateQuestion() throws Exception {
        List<Question> questions = new ArrayList<>();

        when(questionService.saveQuestion(any(Question.class))).then((Answer<Question>) invocation -> {
            Question q = invocation.getArgument(0, Question.class);
            questions.add(q);
            return q;
        });

        this.mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/questions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"text\":\"A+B\",\"explanation\":\"Test\"}")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("{\"id\":0,\"text\":\"A+B\",\"explanation\":\"Test\"}"))
                );

        assertEquals("A+B", questions.get(0).getText());
        assertEquals("Test", questions.get(0).getExplanation());
    }
}