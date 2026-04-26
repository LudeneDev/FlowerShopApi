package com.ludenedev.flowershop.service.adapter;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowershop.model.Flower;
import com.ludenedev.flowershop.service.FlowersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FlowerServiceAdapterTest {

    @Mock
    private FlowerRepository repository;

    @InjectMocks
    private FlowersService service;

    @Test
    void btoa_ShouldMapEntityToModel() {

        UUID id = UUID.randomUUID();
        EntityFlower entity = new EntityFlower();
        entity.setId(id);
        entity.setKind("Rose");
        entity.setQuantity(10);
        entity.setAvgPrice(5.5);


        Flower model = service.btoa(entity);


        assertThat(model.getId()).isEqualTo(id);
        assertThat(model.getKind()).isEqualTo("Rose");
        assertThat(model.getQuantity()).isEqualTo(10);
        assertThat(model.getAvgPrice()).isEqualTo(5.5);
    }

    @Test
    void atob_ShouldMapModelToEntity_WhenIdIsNull() {

        Flower model = new Flower();
        model.setKind("Tulip");
        model.setQuantity(5);
        model.setAvgPrice(2.0);

        EntityFlower entity = service.atob(model);


        assertThat(entity.getId()).isNull(); // Should not call repo if ID is null
        assertThat(entity.getKind()).isEqualTo("Tulip");
        assertThat(entity.getQuantity()).isEqualTo(5);
        assertThat(entity.getAvgPrice()).isEqualTo(2.0);
    }
}