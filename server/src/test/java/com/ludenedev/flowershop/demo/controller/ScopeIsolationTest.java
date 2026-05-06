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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
@Profile("demo")
@Import(DemoTestConfig.class)
class ScopeIsolationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DemoCreationService demoCreationService;

    String tokenA;
    String tokenB;

    String flowerA;
    String flowerB;

    String bouquetA;
    String bouquetB;

    String billA;
    String billB;

    @BeforeEach
    void setup() throws Exception {

        // ---- SESSION A ----
        tokenA = jwtService.createToken();
        demoCreationService.createSession(jwtService.extractSessionId(tokenA));

        String resFlowersA = getUrl("/api/flowers", tokenA);
        flowerA = extract(resFlowersA, "id");

        createBouquet(tokenA, flowerA);

        String resBouquetsA = getUrl("/api/bouquets", tokenA);
        bouquetA = extract(resBouquetsA, "id");

        String resBillsA = getUrl("/api/bills", tokenA);
        billA = extract(resBillsA, "id");


        // ---- SESSION B ----
        tokenB = jwtService.createToken();
        demoCreationService.createSession(jwtService.extractSessionId(tokenB));

        String resFlowersB = getUrl("/api/flowers", tokenB);
        flowerB = extract(resFlowersB, "id");

        createBouquet(tokenB, flowerB);

        String resBouquetsB = getUrl("/api/bouquets", tokenB);
        bouquetB = extract(resBouquetsB, "id");

        String resBillsB = getUrl("/api/bills", tokenB);
        billB = extract(resBillsB, "id");
    }

    // -------- TESTS --------

    @Test
    void flowers_shouldBeScoped() {
        assertThat(flowerA).isNotEqualTo(flowerB);
    }

    @Test
    void bouquets_shouldBeScoped() {
        assertThat(bouquetA).isNotEqualTo(bouquetB);
    }

    @Test
    void bills_shouldBeScoped() {
        assertThat(billA).isNotEqualTo(billB);
    }

    @Test
    void flowers_shouldHaveSameBusinessData() throws Exception {
        String resA = getUrl("/api/flowers", tokenA);
        String resB = getUrl("/api/flowers", tokenB);

        String kindA = extract(resA, "kind");
        String kindB = extract(resB, "kind");

        assertThat(kindA).isEqualTo(kindB);
    }

    // -------- HELPERS --------

    private String getUrl(String url, String token) throws Exception {
        return mockMvc.perform(get(url)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private void createBouquet(String token, String flowerId) throws Exception {
        String json = """
            {
              "items": [
                {
                  "flowerId": "%s",
                  "quantity": 2
                }
              ]
            }
        """.formatted(flowerId);

        mockMvc.perform(post("/api/bouquets")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(json))
                .andReturn();
    }

    private String extract(String json, String field) {
        return json.split("\"" + field + "\":\"")[1].split("\"")[0];
    }
}
