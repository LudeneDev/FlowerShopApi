package com.ludenedev.flowers.flowers.service;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetFlower;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowers.flowers.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowers.flowers.model.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Log4j2
@Service
public class BouquetService implements MySqlAdapterMapper<EntityBouquetItem, BouquetItem> {

    private final BouquetRepository repo;
    private final FlowersService flowerService;

    public BouquetService(BouquetRepository repo, FlowersService flowerService) {
        this.repo = repo;
        this.flowerService = flowerService;
    }


    public List<BouquetItem> getAllBouquets() {
        return repo.findAll().stream()
                .map(this::btoa)
                .toList();
    }

    @Transactional
    public EntityBouquetItem createBouquet(CreateBouquetItem request) {

        EntityBouquetItem ebi = new EntityBouquetItem();
        ebi = ctoa(request);


        return repo.save(ebi);
    }



    @Override
    public EntityBouquetItem atob(BouquetItem source) {
        EntityBouquetItem ebi = new EntityBouquetItem();


        if (source.getId() != null) {
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

            if (cbf.getQuantity() > flower.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock");
            }

            EntityBouquetFlower ebf = new EntityBouquetFlower();
            ebf.setFlowers(flower);
            ebf.setBouquet(ebi);
            ebf.setQuantity(cbf.getQuantity());

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
        return repo.getReferenceById(bi.getId());
    }
}