package de.thb.learnApp.controller;

import de.thb.learnApp.model.Answer;
import de.thb.learnApp.model.Category;
import de.thb.learnApp.model.Question;
import de.thb.learnApp.service.AnswerService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @Test
    public void testGetAnswers() throws Exception {
        List<Answer> answers = new ArrayList<>();
        Answer a1 = new Answer();
        a1.setText("3");
        a1.setCorrect(true);
        answers.add(a1);

        when(answerService.getAnswers()).thenReturn(answers);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/answers"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("[{\"id\":0,\"text\":\"3\",\"isCorrect\":true}]"))
                );
    }

    @Test
    public void testCreateAnswers() throws Exception {
        List<Answer> answers = new ArrayList<>();

        JSONObject input = new JSONObject();
        input.put("text", "5");
        input.put("isCorrect", true);

        JSONObject expected = new JSONObject(input.toString());
        expected.put("id", 0);

        when(answerService.saveAnswers(any(Answer.class))).then(invocation -> {
            Answer a = invocation.getArgument(0, Answer.class);
            answers.add(a);
            return a;
        });

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input.toString())
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(
                        MockMvcResultMatchers.content().
                                json(expected.toString())
                );
    }
}