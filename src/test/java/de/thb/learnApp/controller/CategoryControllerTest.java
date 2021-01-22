package de.thb.learnApp.controller;

import de.thb.learnApp.model.Category;
import de.thb.learnApp.service.CategoryService;
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
        categories.add(c);

        when(categoryService.getCategory()).thenReturn(categories);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.content().
                                string(containsString("[{\"id\":0,\"description\":\"Addition\",\"title\":\"Math\"}]"))
                );
    }

    @Test
    public void testCreateCategory() throws Exception {
        List<Category> categories = new ArrayList<>();

        when(categoryService.saveCategory(any(Category.class))).then(invocation -> {
            Category c = invocation.getArgument(0, Category.class);
            categories.add(c);
            return c;
        });

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
                                string(containsString("{\"id\":0,\"description\":\"Addition\",\"title\":\"Math\"}"))
                );

        assertEquals("Math", categories.get(0).getTitle());
        assertEquals("Addition", categories.get(0).getDescription());
    }
}