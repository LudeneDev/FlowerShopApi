package com.ludenedev.flowers.flowers.service;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowers.flowers.adapter.mysql.repositories.BillRepository;
import com.ludenedev.flowers.flowers.model.Bill;
import com.ludenedev.flowers.flowers.model.BouquetItem;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class BillService implements MySqlAdapterMapper<EntityBill, Bill> {

    private final BillRepository repo;
    private final BouquetService bouquetService;

    public BillService(BillRepository repo, BouquetService bouquetService) {
        this.repo = repo;
        this.bouquetService = bouquetService;
    }


    @Transactional(readOnly = true)
    public List<Bill> getAllBills() {
        return repo.findAll().stream()
                .map(this::btoa)
                .toList();
    }

    @Transactional(readOnly = true)
    public Bill getBillById(UUID id) {
        return repo.findById(id)
                .map(this::btoa)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with id: " + id));
    }


    @Override
    public EntityBill atob(Bill source) {
        if (Objects.isNull(source)) return null;

        EntityBill bill = new EntityBill();
        bill.setCreatedAt(source.getCreatedAt());
        bill.setTotalPrice(source.getTotalPrice());

        for (BouquetItem bi : source.getItems()) {

            EntityBouquetItem ebi = bouquetService.getByModel(bi);
            bill.getItems().add(ebi);
        }
        return bill;
    }

    @Override
    public Bill btoa(EntityBill source) {
        Bill bill = new Bill();
        bill.setId(source.getId());
        bill.setCreatedAt(source.getCreatedAt());
        bill.setTotalPrice(source.getTotalPrice());

        for (EntityBouquetItem ebi : source.getItems()) {
            BouquetItem bi = bouquetService.btoa(ebi);
            bill.addItemsItem(bi);
        }
        return bill;
    }
}