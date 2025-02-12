package Expense.Tracker.demo.controller;

import Expense.Tracker.demo.model.Expense;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String authToken;
    private String uniqueUsername;

    @BeforeEach
    void setUp() throws Exception {
        // Generate a unique username for each test run
        uniqueUsername = "testuser_" + UUID.randomUUID().toString().substring(0, 8);

        // First register a new user
        RegisterRequest registerRequest = new RegisterRequest(uniqueUsername, "password123");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        // Then login to get auth token
        AuthRequest loginRequest = new AuthRequest(uniqueUsername, "password123");
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(response, AuthResponse.class);
        authToken = authResponse.token();
    }

    @Test
    void createAndGetExpense() throws Exception {

        Expense expense = new Expense();
        expense.setAmount(100.0);
        expense.setCategory("Food");
        expense.setDescription("Test expense");

        String expenseJson = objectMapper.writeValueAsString(expense);


        MvcResult createResult = mockMvc.perform(post("/api/expenses")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expenseJson))
                .andExpect(status().isCreated())
                .andReturn();

        String createResponse = createResult.getResponse().getContentAsString();
        Expense createdExpense = objectMapper.readValue(createResponse, Expense.class);

        // Get created expense
        mockMvc.perform(get("/api/expenses/" + createdExpense.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    void registerAndLoginUser() throws Exception {
        String uniqueTestUsername = "testuser_" + UUID.randomUUID().toString().substring(0, 8);

        // Register new user
        RegisterRequest registerRequest = new RegisterRequest(uniqueTestUsername, "password123");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(uniqueTestUsername))
                .andExpect(jsonPath("$.message").value("User registered successfully"));

        // Login with new user
        AuthRequest loginRequest = new AuthRequest(uniqueTestUsername, "password123");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }


    /*@Test
    void registerDuplicateUser_ShouldReturnError() throws Exception {
        // Try to register the same username twice
        RegisterRequest registerRequest = new RegisterRequest(uniqueUsername, "password123");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void loginWithInvalidCredentials_ShouldReturnError() throws Exception {
        AuthRequest loginRequest = new AuthRequest(uniqueUsername, "wrongpassword");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }*/

}
