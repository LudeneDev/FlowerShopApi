package com.ludenedev.flowershop.controller;


import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowershop.model.CreateBouquetFlower;
import com.ludenedev.flowershop.model.CreateBouquetItem;
import com.ludenedev.flowershop.service.BouquetService;
import com.ludenedev.flowershop.service.FlowersService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Import(BouquetService.class)
class BouquetControllerTest {

    @Autowired
    MockMvc mockMvc;


    @MockitoBean
    BouquetRepository repo;

    @MockitoBean
    FlowersService flowerService;

    String PATH = "/api/bouquets";

    @Test
    void testPostBouquet() throws Exception {
        String body = """
                {
                  "items" : [
                    {
                      "flowerId" : "%s",
                      "quantity" : %s
                    }
                  ]
                }
                """;

        CreateBouquetItem request = new CreateBouquetItem();
        CreateBouquetFlower cbf = new CreateBouquetFlower();
        UUID flowerId = UUID.randomUUID();
        cbf.setFlowerId(flowerId);
        cbf.setQuantity(2);
        request.setItems(List.of(cbf));
        body = body.formatted(flowerId, 2);
        EntityFlower flower = new EntityFlower();
        flower.setId(flowerId);
        flower.setAvgPrice(10.0);
        flower.setQuantity(10);
        when(flowerService.getById(flowerId)).thenReturn(flower);

        doNothing().when(flowerService).updateQuantity(any(), anyInt());

        EntityBouquetItem savedEntity = new EntityBouquetItem();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setBill(new EntityBill());
        when(repo.save(any(EntityBouquetItem.class))).thenReturn(savedEntity);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                .with(httpBasic("admin","secret")))
                .andExpect(status().isCreated());
    }

    @Test
    void testPostBouquetWithInvalidId() throws Exception {
        String body = """
                {
                  "items" : [
                    {
                      "flowerId" : "%s",
                      "quantity" : %s
                    }
                  ]
                }
                """;


        body = body.formatted(UUID.randomUUID(), 2);

        when(flowerService.getById(UUID.randomUUID())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(httpBasic("admin","secret")))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testPostBouquetWithInvalidValue() throws Exception {
        String body = """
                {
                  "items" : [
                    {
                      "flowerId" : "%s",
                      "quantity" : %s
                    }
                  ]
                }
                """;


        body = body.formatted(UUID.randomUUID(), -4);

        when(flowerService.getById(UUID.randomUUID())).thenReturn(new EntityFlower());

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(httpBasic("admin","secret")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetBouquets() throws Exception {
        EntityBill bill = new EntityBill();
        bill.setId(UUID.randomUUID());

        EntityBouquetItem item = new EntityBouquetItem();
        item.setId(UUID.randomUUID());
        item.setBill(bill);

        when(repo.findAll()).thenReturn(List.of(item));

        mockMvc.perform(get(PATH)
                .with(httpBasic("admin","secret")))
                .andExpect(status().isOk());
    }
}
