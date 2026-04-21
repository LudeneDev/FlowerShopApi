package com.ludenedev.flowershop.service.adapter;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.repositories.BillRepository;
import com.ludenedev.flowershop.model.Bill;
import com.ludenedev.flowershop.model.BouquetItem;
import com.ludenedev.flowershop.service.BillService;
import com.ludenedev.flowershop.service.BouquetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillServiceAdapterTest {

    @Mock
    private BillRepository repo;
    @Mock
    private BouquetService bouquetService;

    @InjectMocks
    private BillService billService;

    @Test
    void atob_ShouldMapAndLinkBouquetItems() {
        Bill model = new Bill();
        model.setTotalPrice(100.0);
        model.setCreatedAt(OffsetDateTime.now());

        BouquetItem bi = new BouquetItem();
        bi.setId(UUID.randomUUID());
        model.setItems(List.of(bi));

        EntityBouquetItem ebi = new EntityBouquetItem();
        ebi.setId(bi.getId());
        when(bouquetService.getByModel(bi)).thenReturn(ebi);

        EntityBill result = billService.atob(model);

        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo(100.0);
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getItems().toArray()[0]).isEqualTo(ebi);
        verify(bouquetService).getByModel(bi);
    }

    @Test
    void btoa_ShouldMapEntityBillToModel() {
        EntityBill eb = new EntityBill();
        UUID id = UUID.randomUUID();
        eb.setId(id);
        eb.setTotalPrice(100.0);
        eb.setCreatedAt(OffsetDateTime.now());

        EntityBouquetItem ebi = new EntityBouquetItem();
        UUID itemId = UUID.randomUUID();
        ebi.setId(itemId);
        eb.getItems().add(ebi);

        BouquetItem bi = new BouquetItem();
        bi.setId(itemId);
        when(bouquetService.btoa(any())).thenReturn(bi);

        Bill result = billService.btoa(eb);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTotalPrice()).isEqualTo(100.0);
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getItems().get(0).getId()).isEqualTo(itemId);
    }
}