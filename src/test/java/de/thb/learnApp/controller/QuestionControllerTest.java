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

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private QuestionService questionService;

    @Test
    public void testGetQuestions() throws Exception {
        List<Question> questions = new ArrayList<>();

        Question q1 = new Question();
        q1.setText("A+B");
        q1.setExplanation("Test");
        q1.setHash("DVwdgtw%s&");
        Answer a1 = new Answer();
        a1.setText("A+B");
        a1.setCorrect(true);
        List<Answer> answers = new ArrayList<>();
        answers.add(a1);
        Category c1 = new Category();
        c1.setDescription("Addition of two letters");
        c1.setTitle("Addition");
        c1.setHash("%/sdvgwwRRss&");
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
                                string(containsString("[{\"id\":0,\"text\":\"A+B\"," +
                                            "\"explanation\":\"Test\",\"hash\":\"DVwdgtw%s&\"," +
                                        "\"answers\":[{\"id\":0,\"text\":\"A+B\",\"isCorrect\":true}]," +
                                        "\"category\":{\"id\":0,\"description\":\"Addition of two letters\"," +
                                            "\"title\":\"Addition\",\"hash\":\"%/sdvgwwRRss&\"}}]"))
                );
    }

    @Test
    public void testCreateQuestion() throws Exception {
        List<Category> categories = new ArrayList<>();

        JSONObject inputCat = new JSONObject();
        inputCat.put("title", "Math");
        inputCat.put("description", "Addition");
        inputCat.put("hash", "CWGBJ$$,113FJ1H");

        JSONObject expectedCategory = new JSONObject(inputCat.toString());
        expectedCategory.put("id", 0);

        when(categoryService.saveCategory(any(Category.class))).then(invocation -> {
            Category c = invocation.getArgument(0, Category.class);
            categories.add(c);
            return c;
        });

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputCat.toString())
        )
                .andDo(print())
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                json(expectedCategory.toString())
                );

        List<Question> questions = new ArrayList<>();

        JSONObject inputQuestion = new JSONObject();
        inputQuestion.put("text", "A+B");
        inputQuestion.put("explanation", "Test");
        inputQuestion.put("hash", "HDVVSsvgd$$s//vdsg");
        inputQuestion.put("answers", new JSONArray()
                .put(new JSONObject()
                        .put("text", "answer1")
                        .put("isCorrect", true))
        );
        inputQuestion.put("category", new JSONObject()
                .put("id", 0));


        JSONObject expectedQuestion = new JSONObject(inputQuestion.toString());
        expectedQuestion.put("id", 0);

        when(questionService.saveQuestion(any(Question.class))).then(invocation -> {
            Question q = invocation.getArgument(0, Question.class);
            questions.add(q);
            return q;
        });

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputQuestion.toString())
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.content().json(expectedQuestion.toString())
                );
    }
}