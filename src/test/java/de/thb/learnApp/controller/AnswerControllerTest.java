package de.thb.learnApp.controller;

import de.thb.learnApp.model.Answer;
import de.thb.learnApp.model.Question;
import de.thb.learnApp.service.AnswerService;
import org.junit.jupiter.api.Test;
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
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @Test
    public void testGetAnswers() throws Exception {
        List<Answer> answers = new ArrayList<>();
        Question q1 = new Question();
        q1.setText("1+2");
        q1.setExplanation("Test");

        Answer a1 = new Answer();
        a1.setText("3");
        a1.setCorrect(true);
        a1.setQuestion(q1);
        answers.add(a1);

        when(answerService.getAnswers()).thenReturn(answers);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/answers"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("[{\"id\":0,\"text\":\"3\",\"isCorrect\":true," +
                                        "\"question\":{\"id\":0,\"text\":\"1+2\",\"explanation\":\"Test\"," +
                                        "\"answers\":[]," +
                                        "\"category\":null}}]"))
                );
    }

    @Test
    public void testCreateAnswers() throws Exception {
        List<Answer> answers = new ArrayList<>();

        when(answerService.saveAnswers(any(Answer.class))).then(invocation -> {
            Answer a = invocation.getArgument(0, Answer.class);
            answers.add(a);
            return a;
        });

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"text\":\"A\",\"isCorrect\":true," +
                                "\"question\":{\"id\":0,\"text\":\"1+2\",\"explanation\":\"Test\"," +
                                "\"answers\":[]}}")
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(
                        MockMvcResultMatchers.content().
                                json("{\"id\":0,\"text\":\"A\",\"isCorrect\":true,\"question\":null}")
                );

        assertEquals("A", answers.get(0).getText());
    }
}