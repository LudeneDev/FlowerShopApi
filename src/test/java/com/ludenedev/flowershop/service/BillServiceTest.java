package com.ludenedev.flowershop.service;


import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.repositories.BillRepository;
import com.ludenedev.flowershop.model.Bill;
import com.ludenedev.flowershop.model.BouquetItem;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillRepository repository;

    @Mock
    private BouquetService bouquetService;

    @InjectMocks
    private BillService billService;

    @Test
    void getAllBills_ShouldReturnMappedList() {
        EntityBill entity = new EntityBill();
        entity.setId(UUID.randomUUID());
        entity.setTotalPrice(100.0);

        when(repository.findAll()).thenReturn(List.of(entity));

        List<Bill> result = billService.getAllBills();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTotalPrice()).isEqualTo(100.0);
        verify(repository).findAll();
    }

    @Test
    void getBillById_ShouldReturnBill_WhenExists() {

        UUID billId = UUID.randomUUID();
        EntityBill entityBill = new EntityBill();
        entityBill.setId(billId);
        entityBill.setTotalPrice(50.0);

        EntityBouquetItem entityItem = new EntityBouquetItem();
        entityItem.setId(UUID.randomUUID());
        entityBill.setItems(Set.of(entityItem));

        when(repository.findById(billId)).thenReturn(Optional.of(entityBill));

        BouquetItem dtoItem = new BouquetItem();
        dtoItem.setId(entityItem.getId());
        when(bouquetService.btoa(entityItem)).thenReturn(dtoItem);

        Bill result = billService.getBillById(billId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(billId);
        assertThat(result.getItems()).hasSize(1);

        verify(repository).findById(billId);
    }

    @Test
    void getBillById_ShouldThrowException_WhenNotFound() {
        UUID billId = UUID.randomUUID();
        when(repository.findById(billId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> billService.getBillById(billId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Bill not found");
    }
}
