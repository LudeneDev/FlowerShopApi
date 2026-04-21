package com.ludenedev.flowershop.controller;


import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.repositories.BillRepository;
import com.ludenedev.flowershop.model.BouquetItem;
import com.ludenedev.flowershop.service.BillService;
import com.ludenedev.flowershop.service.BouquetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@SpringBootTest
@Import(BillService.class)
class BillControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BillRepository repo;

    @MockitoBean
     BouquetService bouquetService;

    @Test
    void testGetAllBills() throws Exception {
        EntityBill bill = new EntityBill();
        bill.setId(UUID.randomUUID());
        bill.setTotalPrice(100.0);

        when(repo.findAll()).thenReturn(List.of(bill));

        mockMvc.perform(get("/bills")
                .with(httpBasic("admin","secret")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBillById() throws Exception {
        UUID id = UUID.randomUUID();
        EntityBill bill = new EntityBill();
        bill.setId(id);
        bill.setTotalPrice(50.0);

        EntityBouquetItem item = new EntityBouquetItem();
        item.setId(UUID.randomUUID());
        bill.getItems().add(item);

        BouquetItem modelItem = new BouquetItem();
        modelItem.setId(item.getId());

        when(repo.findById(id)).thenReturn(Optional.of(bill));
        when(bouquetService.btoa(any(EntityBouquetItem.class))).thenReturn(modelItem);

        mockMvc.perform(get("/bills/{id}",id)

                .with(httpBasic("admin","secret")))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidBillId() throws Exception {
        UUID id = UUID.randomUUID();
        EntityBill bill = new EntityBill();
        bill.setId(id);
        bill.setTotalPrice(50.0);

        EntityBouquetItem item = new EntityBouquetItem();
        item.setId(UUID.randomUUID());
        bill.getItems().add(item);

        BouquetItem modelItem = new BouquetItem();
        modelItem.setId(item.getId());

        when(repo.findById(id)).thenReturn(Optional.empty());
        when(bouquetService.btoa(any(EntityBouquetItem.class))).thenReturn(null);

        mockMvc.perform(get("/bills/{id}",id)

                        .with(httpBasic("admin","secret")))
                .andExpect(status().isNotFound());
    }
}
