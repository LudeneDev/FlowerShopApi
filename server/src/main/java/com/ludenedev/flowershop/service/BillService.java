package com.ludenedev.flowershop.service;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.repositories.BillRepository;
import com.ludenedev.flowershop.demo.DemoContext;
import com.ludenedev.flowershop.demo.entities.DemoEntityBill;
import com.ludenedev.flowershop.demo.repositories.DemoBillRepository;
import com.ludenedev.flowershop.model.Bill;
import com.ludenedev.flowershop.model.BouquetItem;
import com.ludenedev.flowershop.service.providers.BillProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BillService implements MySqlAdapterMapper<EntityBill, Bill> {

    private final BillRepository repo;
    private final BouquetService bouquetService;
    private final BillProvider provider;



    @Transactional(readOnly = true)
    public List<Bill> getAllBills() {
        return provider.getAll().stream()
                .map(this::btoa)
                .toList();
    }

    @Transactional(readOnly = true)
    public Bill getBillById(UUID id)
    {
        provider.checkForSession(List.of(id));
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