package com.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // 如果需要，可以在此处进行设置
    }

    @Test
    public void login_UsernameIsNull_ReturnsUsernameRequired() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\": 400, \"message\": \"Username is required\"}"));
    }

    @Test
    public void login_UsernameIsEmpty_ReturnsUsernameRequired() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\": 400, \"message\": \"Username is required\"}"));
    }

    @Test
    public void login_PasswordIsNull_ReturnsPasswordRequired() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\": 400, \"message\": \"Password is required\"}"));
    }

    @Test
    public void login_PasswordIsEmpty_ReturnsPasswordRequired() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "admin")
                .param("password", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\": 400, \"message\": \"Password is required\"}"));
    }

    @Test
    public void login_ValidCredentials_ReturnsLoginSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "admin")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\": 200, \"message\": \"Login successful!\"}"));
    }

    @Test
    public void login_InvalidCredentials_ReturnsInvalidCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "user")
                .param("password", "pass"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"code\": 401, \"message\": \"Invalid credentials!\"}"));
    }
}