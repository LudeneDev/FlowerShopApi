package com.ludenedev.flowershop.service;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowershop.model.CreateBouquetFlower;
import com.ludenedev.flowershop.model.CreateBouquetItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BouquetServiceTest {

    @Mock
    private BouquetRepository repo;

    @Mock
    private FlowersService flowerService;

    @InjectMocks
    private BouquetService service;

    @Test
    void createBouquet_ShouldCalculatePriceCorrectly() {
        UUID flowerId = UUID.randomUUID();
        CreateBouquetItem request = new CreateBouquetItem();

        CreateBouquetFlower item = new CreateBouquetFlower();
        item.setFlowerId(flowerId);
        item.setQuantity(2);
        request.setItems(List.of(item));

        EntityFlower flower = new EntityFlower();
        flower.setId(flowerId);
        flower.setAvgPrice(10.0);
        flower.setQuantity(10);

        when(flowerService.getById(flowerId)).thenReturn(flower);

        when(repo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        EntityBouquetItem result = service.createBouquet(request);

        assertThat(result).isNotNull();
    }

    @Test
    void createBouquet_ShouldThrowException_WhenNotEnoughStock() {
        UUID flowerId = UUID.randomUUID();
        CreateBouquetItem request = new CreateBouquetItem();

        CreateBouquetFlower item = new CreateBouquetFlower();
        item.setFlowerId(flowerId);
        item.setQuantity(100);
        request.setItems(List.of(item));

        EntityFlower flower = new EntityFlower();
        flower.setId(flowerId);
        flower.setQuantity(5);

        when(flowerService.getById(flowerId)).thenReturn(flower);

        assertThatThrownBy(() -> service.createBouquet(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient stock");
    }

    @Test
    void createBouquet_ShouldThrowException_WhenNoEntity() {
        UUID flowerId = UUID.randomUUID();
        CreateBouquetItem request = new CreateBouquetItem();

        CreateBouquetFlower item = new CreateBouquetFlower();
        item.setFlowerId(flowerId);
        item.setQuantity(100);
        request.setItems(List.of(item));


        when(flowerService.getById(flowerId)).thenReturn(null);

        assertThatThrownBy(() -> service.createBouquet(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void createBouquet_ShouldThrowException_WhenNoID() {
        UUID flowerId = UUID.randomUUID();
        CreateBouquetItem request = new CreateBouquetItem();

        CreateBouquetFlower item = new CreateBouquetFlower();
        item.setFlowerId(flowerId);
        item.setQuantity(100);
        request.setItems(List.of(item));


        when(flowerService.getById(flowerId)).thenReturn(null);

        assertThatThrownBy(() -> service.createBouquet(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
