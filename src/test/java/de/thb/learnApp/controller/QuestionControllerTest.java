package de.thb.learnApp.controller;

import de.thb.learnApp.model.Category;
import de.thb.learnApp.model.Question;
import de.thb.learnApp.model.Answer;
import de.thb.learnApp.service.CategoryService;
import de.thb.learnApp.service.QuestionService;
import org.json.JSONArray;
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
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private CategoryService categoryService;

    @MockBean
    private QuestionService questionService;

    @Test
    public void testGetQuestions() throws Exception {
        List<Question> questions = new ArrayList<>();

        Question q1 = new Question();
        q1.setText("A+B");
        q1.setExplanation("Test");
        Answer a1 = new Answer();
        a1.setText("A+B");
        a1.setCorrect(true);
        List<Answer> answers = new ArrayList<>();
        answers.add(a1);
        Category c1 = new Category();
        c1.setDescription("Addition of two letters");
        c1.setTitle("Addition");
        q1.setCategory(c1);
        q1.setAnswers(answers);

        questions.add(q1);

        when(questionService.getQuestions()).thenReturn(questions);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/questions"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("[{\"id\":0,\"text\":\"A+B\",\"explanation\":\"Test\"," +
                                        "\"answers\":[{\"id\":0,\"text\":\"A+B\",\"isCorrect\":true}]," +
                                        "\"category\":{\"id\":0,\"description\":\"Addition of two letters\",\"title\":\"Addition\"}}]"))
                );
    }

    @Test
    public void testCreateQuestion() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Addition\",\"title\":\"Math\"}")
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("{\"id\":1,\"description\":\"Addition\",\"title\":\"Math\"}"))
                );

        List<Question> questions = new ArrayList<>();

        when(questionService.saveQuestion(any(Question.class))).then(invocation -> {
            Question q = invocation.getArgument(0, Question.class);
            questions.add(q);
            return q;
        });

        this.mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/questions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("[\"text\":\"A+B\",\"explanation\":\"Test\"," +
                                    "\"answers\":[{\"id\":0,\"text\":\"A+B\",\"isCorrect\":true}]," +
                                    "\"category\":{\"id\":1}]}")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("{\"id\":0,\"text\":\"A+B\",\"explanation\":\"Test\"," +
                                        "\"answers\":[{\"id\":0,\"text\":\"A+B\",\"isCorrect\":true,\"question\":null}]," +
                                        "\"category\":{\"id\":1,\"description\":\"Addition\",\"title\":\"Math\"}}"))
                );

        assertEquals("A+B", questions.get(0).getText());
        assertEquals("Test", questions.get(0).getExplanation());
    }

    @Test
    public void testCreateQuestionComplex() throws Exception {
        JSONObject input = new JSONObject();
        input.put("text", "A+B");
        input.put("explanation", "Test");
        input.put("answers", new JSONArray()
                .put(new JSONObject()
                        .put("text", "answer1")
                        .put("isCorrect", true))
        );
        input.put("category", JSONObject.NULL);

        JSONObject expected = new JSONObject(input.toString());
        expected.put("id", 0);

        List<Question> questions = new ArrayList<>();

        when(questionService.saveQuestion(any(Question.class))).then(invocation -> {
            Question q = invocation.getArgument(0, Question.class);
            questions.add(q);
            return q;
        });

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input.toString())
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.content().json(expected.toString())
                );

    }
}