package com.ludenedev.flowershop.service.adapter;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetFlower;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowershop.model.BouquetFlower;
import com.ludenedev.flowershop.model.BouquetItem;
import com.ludenedev.flowershop.model.CreateBouquetFlower;
import com.ludenedev.flowershop.model.CreateBouquetItem;
import com.ludenedev.flowershop.model.Flower;
import com.ludenedev.flowershop.service.BouquetService;
import com.ludenedev.flowershop.service.FlowersService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BouquetServiceAdapterTest {

    @Mock
    private BouquetRepository repo;
    @Mock
    private FlowersService flowerService;

    @InjectMocks
    private BouquetService service;

    @Test
    void atob_ShouldReturnReference_WhenIdIsNotNull() {
        UUID id = UUID.randomUUID();
        BouquetItem model = new BouquetItem();
        model.setId(id);

        EntityBouquetItem entity = new EntityBouquetItem();
        when(repo.getReferenceById(id)).thenReturn(entity);

        EntityBouquetItem result = service.atob(model);

        assertThat(result).isNotNull();
        verify(repo).getReferenceById(id);
    }

    @Test
    void atob_ShouldMapNewEntity_WhenIdIsNull() {
        BouquetItem model = new BouquetItem();
        BouquetFlower bf = new BouquetFlower();
        Flower f = new Flower();
        UUID fid = UUID.randomUUID();
        f.setId(fid);
        bf.setFlower(f);
        bf.setQuantity(2);
        model.setItems(List.of(bf));

        EntityFlower ef = new EntityFlower();
        ef.setAvgPrice(10.0);
        when(flowerService.getById(fid)).thenReturn(ef);

        EntityBouquetItem result = service.atob(model);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getBill()).isNotNull();
        assertThat(result.getBill().getTotalPrice()).isEqualTo(20.0);
    }

    @Test
    void ctoa_ShouldMapAndCalculatePrice() {
        CreateBouquetItem request = new CreateBouquetItem();
        CreateBouquetFlower cbf = new CreateBouquetFlower();
        UUID fid = UUID.randomUUID();
        cbf.setFlowerId(fid);
        cbf.setQuantity(2);
        request.setItems(List.of(cbf));

        EntityFlower f = new EntityFlower();
        f.setId(fid);
        f.setAvgPrice(10.0);
        f.setQuantity(10);
        when(flowerService.getById(fid)).thenReturn(f);

        doNothing().when(flowerService).updateQuantity(any(), anyInt());

        EntityBouquetItem result = service.ctoa(request);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getBill()).isNotNull();
        assertThat(result.getBill().getTotalPrice()).isEqualTo(20.0);
    }

    @Test
    void ctoa_ShouldThrowException_WhenStockInsufficient() {
        CreateBouquetItem request = new CreateBouquetItem();
        CreateBouquetFlower cbf = new CreateBouquetFlower();
        UUID fid = UUID.randomUUID();
        cbf.setFlowerId(fid);
        cbf.setQuantity(100);
        request.setItems(List.of(cbf));

        EntityFlower f = new EntityFlower();
        f.setQuantity(10);
        when(flowerService.getById(fid)).thenReturn(f);

        assertThatThrownBy(() -> service.ctoa(request)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void btoa_ShouldMapEntityBouquetItemToModel() {
        EntityBouquetItem ebi = new EntityBouquetItem();
        ebi.setId(UUID.randomUUID());

        EntityBill bill = new EntityBill();
        UUID billId = UUID.randomUUID();
        bill.setId(billId);
        ebi.setBill(bill);

        EntityFlower ef = new EntityFlower();
        ef.setKind("Rose");
        EntityBouquetFlower ebf = new EntityBouquetFlower();
        ebf.setFlowers(ef);
        ebi.getItems().add(ebf);

        Flower f = new Flower();
        f.setKind("Rose");
        when(flowerService.btoa(any())).thenReturn(f);

        BouquetItem result = service.btoa(ebi);

        assertThat(result.getId()).isEqualTo(ebi.getId());
        assertThat(result.getBillId()).isEqualTo(billId);
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getItems().get(0).getFlower().getKind()).isEqualTo("Rose");
    }
}