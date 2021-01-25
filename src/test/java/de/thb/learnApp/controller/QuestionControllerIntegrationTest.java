package de.thb.learnApp.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class QuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateQuestionComplex() throws Exception {
        JSONObject category = new JSONObject();
        category.put("description", "Foo");
        category.put("title", "Bar");
        category.put("hash", "dbasbdzze!dd44&");

        JSONObject expectedCategory = new JSONObject(category.toString());
        expectedCategory.put("id", 1);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(category.toString())
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                json(expectedCategory.toString())
                );

        JSONObject input = new JSONObject();
        input.put("text", "A+B");
        input.put("explanation", "Test");
        input.put("hash", "sHSDgsd$55");
        input.put("answers", new JSONArray()
                .put(new JSONObject()
                        .put("text", "answer1")
                        .put("isCorrect", true))
        );
        input.put("category", new JSONObject()
                .put("id", 1));

        JSONObject expected = new JSONObject(input.toString());
        expected.put("id", 1);

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

    @Test
    public void testCreateQuestionComplexWithExistingCategory() throws Exception {
        JSONObject category = new JSONObject();
        category.put("description", "Foo");
        category.put("title", "Bar");
        category.put("hash", "dbasbdzze!dd44&");

        JSONObject expectedCategory = new JSONObject(category.toString());
        expectedCategory.put("id", 1);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(category.toString())
        )
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isCreated()
        )
        .andExpect(
                MockMvcResultMatchers.content().
                        json(expectedCategory.toString())
        );

        JSONObject input = new JSONObject();
        input.put("text", "A+B");
        input.put("explanation", "Test");
        input.put("hash", "BFheTT3z4&&5");
        input.put("answers", new JSONArray()
                .put(new JSONObject()
                        .put("text", "answer1")
                        .put("isCorrect", true))
        );
        input.put("category", new JSONObject()
                .put("id", 1));

        JSONObject expected = new JSONObject(input.toString());
        expected.put("id", 1);

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