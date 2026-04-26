package com.ludenedev.flowershop.service;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowershop.model.CreateFlower;
import com.ludenedev.flowershop.model.Flower;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlowerServiceTest {

    @Mock
    private FlowerRepository repository;

    @InjectMocks
    private FlowersService service;

    @Test
    void getAllFlowers_ShouldReturnMappedList() {

        UUID id = UUID.randomUUID();
        EntityFlower entity = new EntityFlower();
        entity.setId(id);
        entity.setKind("Rose");
        entity.setQuantity(10);
        entity.setAvgPrice(5.0);

        when(repository.findAll()).thenReturn(List.of(entity));


        List<Flower> result = service.getAllFlowers();


        assertThat(result).hasSize(1);
        assertThat(result.get(0).getKind()).isEqualTo("Rose");
        verify(repository).findAll();
    }

    @Test
    void createFlower_ShouldMapAndSave() {

        CreateFlower dto = new CreateFlower();
        dto.setKind("Tulip");
        dto.setQuantity(5);
        dto.setAvgPrice(2.5);

        EntityFlower savedEntity = new EntityFlower();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setKind("Tulip");
        savedEntity.setQuantity(5);
        savedEntity.setAvgPrice(2.5);

        when(repository.save(any(EntityFlower.class))).thenReturn(savedEntity);

        Flower result = service.createFlower(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getKind()).isEqualTo("Tulip");

        // Verify we attempted to save
        verify(repository).save(any(EntityFlower.class));
    }


}