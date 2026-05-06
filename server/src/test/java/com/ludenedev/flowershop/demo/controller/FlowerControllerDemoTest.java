package com.ludenedev.flowershop.demo.controller;

import com.ludenedev.flowershop.demo.DemoTestConfig;
import com.ludenedev.flowershop.demo.service.DemoCreationService;
import com.ludenedev.flowershop.demo.service.JwtService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
@Profile("demo")
@Import(DemoTestConfig.class)
class FlowerControllerDemoTest {

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
    void getAll_shouldReturnScopedFlowerWithDifferentId() throws Exception {

        var baseId = DemoTestConfig.flower.getId();

        var result = mockMvc.perform(get("/api/flowers")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // minimal structural check first
        assertThat(result).isNotNull();
        assertThat(result).contains("kind");

        // critical assertion: scoped identity difference
        assertThat(result).doesNotContain(baseId.toString());
    }



}