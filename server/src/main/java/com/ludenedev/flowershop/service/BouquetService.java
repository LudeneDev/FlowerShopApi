package com.ludenedev.flowershop.service;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetFlower;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowershop.demo.DemoContext;
import com.ludenedev.flowershop.demo.entities.DemoEntityBill;
import com.ludenedev.flowershop.demo.entities.DemoEntityBouquetItem;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import com.ludenedev.flowershop.demo.repositories.DemoBillRepository;
import com.ludenedev.flowershop.demo.repositories.DemoBouquetItemRepository;
import com.ludenedev.flowershop.demo.repositories.DemoSessionRepository;
import com.ludenedev.flowershop.model.*;
import com.ludenedev.flowershop.service.providers.BouquetProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@AllArgsConstructor
public class BouquetService implements MySqlAdapterMapper<EntityBouquetItem, BouquetItem> {

    private final BouquetRepository repo;
    private final FlowersService flowerService;
    private final BouquetProvider provider;





    public List<BouquetItem> getAllBouquets() {
        return provider.getAll().stream()
                .map(this::btoa)
                .toList();
    }

    @Transactional
    public EntityBouquetItem createBouquet(CreateBouquetItem request) {

        EntityBouquetItem ebi = ctoa(request);
        ebi = repo.save(ebi);
        Bill bill = new Bill();
        bill.setId(ebi.getBill().getId());
        bill.setTotalPrice(ebi.getBill().getTotalPrice());
        bill.addItemsItem(btoa(ebi));
        provider.afterCreate(ebi);


        return repo.save(ebi);
    }



    @Override
    public EntityBouquetItem atob(BouquetItem source) {
        EntityBouquetItem ebi = new EntityBouquetItem();


        if (source.getId() != null) {
            provider.checkForSession(List.of(source.getId()));
            return repo.getReferenceById(source.getId());
        }

        EntityBill bill = new EntityBill();
        bill.getItems().add(ebi);

        final double[] price = {(double) 0};
        List<EntityBouquetFlower> ebfs = source.getItems().stream().map(x -> {
            EntityBouquetFlower ebf = new EntityBouquetFlower();
            EntityFlower ef;
            try {
                ef = flowerService.getById(x.getFlower().getId());
            } catch (EntityNotFoundException e) {
                log.error("Flower not found: {}", x.getFlower().getId());

                throw new RuntimeException("Flower not found", e);
            } catch (NullPointerException e) {
                log.error("ID is null");
                throw new RuntimeException("Flower ID is missing", e);
            }

            ebf.setFlowers(ef);
            ebf.setQuantity(x.getQuantity());
            ebf.setBouquet(ebi);


            price[0] += ef.getAvgPrice() * x.getQuantity();

            return ebf;
        }).toList();

        ebi.getItems().addAll(ebfs);
        bill.setTotalPrice(price[0]);
        ebi.setBill(bill);
        bill.setCreatedAt(OffsetDateTime.now());

        return ebi;
    }

    public EntityBouquetItem ctoa(CreateBouquetItem source) {
        EntityBouquetItem ebi = new EntityBouquetItem();
        double price = 0;

        for (CreateBouquetFlower cbf : source.getItems()) {
            EntityFlower flower = flowerService.getById(cbf.getFlowerId());


            if(Objects.isNull(flower)) throw  new IllegalArgumentException("Flower is null");

            if (cbf.getQuantity() > flower.getQuantity() && cbf.getQuantity() > 0) {
                throw new IllegalArgumentException("Insufficient stock");
            }


            EntityBouquetFlower ebf = new EntityBouquetFlower();
            ebf.setFlowers(flower);
            ebf.setBouquet(ebi);
            ebf.setQuantity(cbf.getQuantity());
            int updateQuantity = flower.getQuantity() - cbf.getQuantity();
            flowerService.updateQuantity(flower.getId(), updateQuantity);

            ebi.getItems().add(ebf);
            price += flower.getAvgPrice() * cbf.getQuantity();
        }

        EntityBill bill = new EntityBill();
        bill.setCreatedAt(OffsetDateTime.now());
        bill.setTotalPrice(price);
        bill.getItems().add(ebi);

        ebi.setBill(bill);
        return ebi;
    }

    @Override
    public BouquetItem btoa(EntityBouquetItem source) {
        BouquetItem bi = new BouquetItem();
        bi.setId(source.getId());
        if (source.getBill() != null) {
            bi.setBillId(source.getBill().getId());
        }

        for (EntityBouquetFlower ebf : source.getItems()) {
            BouquetFlower bf = new BouquetFlower();
            bf.setId(ebf.getId());
            bf.setQuantity(ebf.getQuantity());

            Flower f = flowerService.btoa(ebf.getFlowers());
            bf.setFlower(f);

            bi.addItemsItem(bf);
        }
        return bi;
    }

    public EntityBouquetItem getByModel(BouquetItem bi) {
        if (bi.getId() == null) throw new IllegalArgumentException("ID cannot be null");
        provider.checkForSession(List.of(bi.getId()));
        return repo.getReferenceById(bi.getId());
    }
}