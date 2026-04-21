package com.ludenedev.flowershop.security;


import com.ludenedev.flowershop.FlowersApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes= FlowersApplication.class)
public class SecurityTest {

    @Autowired
    MockMvc mvc;


    @Test
    void shouldAuthenticate() throws Exception {
        mvc.perform(get("/actuator"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAuthenticateNotBasicAuth() throws Exception {
        mvc.perform(get("/flowers")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isUnauthorized());

        mvc.perform(get("/bouquets")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isUnauthorized());

        mvc.perform(get("/bills")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void shouldAuthenticateWithBasicAuth() throws Exception {
        mvc.perform(get("/flowers")
                        .with(httpBasic("admin", "secret")))
                .andExpect(status().isOk());

        mvc.perform(get("/bouquets")
                        .with(httpBasic("admin", "secret")))
                .andExpect(status().isOk());

        mvc.perform(get("/bills")
                        .with(httpBasic("admin", "secret")))
                .andExpect(status().isOk());
    }
}
