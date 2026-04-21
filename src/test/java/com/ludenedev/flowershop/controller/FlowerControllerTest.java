package com.ludenedev.flowershop.controller;


import com.ludenedev.flowershop.FlowersApplication;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowershop.service.FlowersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes= FlowersApplication.class)
@ExtendWith(MockitoExtension.class)
@Import(FlowersService.class)
public class FlowerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    FlowerRepository flowerRepo;


    @Test
    public void testGetFlowers() throws Exception{
        mvc.perform(get("/flowers")
                .with(httpBasic("admin","secret"))
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPostFlowers() throws Exception{
        String body = """
                {
                  "quantity" : 99,
                  "kind" : "TestFlower",
                  "avgPrice" : 99
                }
                """;
        EntityFlower ef = new EntityFlower();
        ef.setId(UUID.randomUUID());
        ef.setKind("TestFlower");
        ef.setAvgPrice(99D);
        ef.setQuantity(99);
        when(flowerRepo.save(any(EntityFlower.class))).thenReturn(ef);
        mvc.perform(post("/flowers")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin","secret")
                                )
                ).andExpect(status().isCreated());
    }

    @Test
    public void testPostFlowersWithNegativeOrNullValues() throws Exception{
        String body = """
                {
                  "quantity" : -1,
                  "kind" : "TestFlower",
                  "avgPrice" : 99
                }
                """;

        mvc.perform(post("/flowers")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("admin","secret")
                )
        ).andExpect(status().isBadRequest());

        body = """
                {
                  "quantity" : 99,
                  "kind" : "TestFlower",
                  "avgPrice" : -1
                }
                """;

        mvc.perform(post("/flowers")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("admin","secret")
                )
        ).andExpect(status().isBadRequest());

        body = """
                {
                  "quantity" : 99,
                  "kind" : "",
                  "avgPrice" : 99
                }
                """;

        mvc.perform(post("/flowers")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("admin","secret")
                )
        ).andExpect(status().isBadRequest());


    }
}
