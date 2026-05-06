package com.ludenedev.flowershop.demo.controller;

import com.ludenedev.flowershop.demo.DemoTestConfig;
import com.ludenedev.flowershop.demo.service.DemoCreationService;
import com.ludenedev.flowershop.demo.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
@Profile("demo")
@Import(DemoTestConfig.class)
class BillControllerDemoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DemoCreationService demoCreationService;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtService.createToken();
        demoCreationService.createSession(jwtService.extractSessionId(token));
    }

    @Test
    void getAll_withValidToken_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/bills")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_withoutToken_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/bills"))
                .andExpect(status().isForbidden());
    }
}