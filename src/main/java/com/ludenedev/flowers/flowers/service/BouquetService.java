package com.ludenedev.flowers.flowers.service;


import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetFlower;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowers.flowers.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowers.flowers.model.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class BouquetService implements MySqlAdapterMapper<EntityBouquetItem, BouquetItem> {

    @Autowired
    BouquetRepository repo;
    @Autowired
    private FlowersService service;

    @Override
    public EntityBouquetItem atob(BouquetItem source) {

        EntityBouquetItem ebi = new EntityBouquetItem();
        EntityBill bill = new EntityBill();
        bill.getItems().add(ebi);
        final double[] price = {0};
        List<EntityBouquetFlower> ebfs = source.getItems().stream().map(x -> {
            EntityBouquetFlower ebf = new EntityBouquetFlower();

            EntityFlower ef;
            try {

                ef = service.getById(x.getFlower().getId());
            } catch (EntityNotFoundException e) {
                log.error("There is no Flower with id: {}", x.getFlower().getId());
                return null;
            }catch (NullPointerException e){
                log.error("There is no id");
                return null;
            }
            ebf.setFlowers(ef);


            ebf.setQuantity(x.getQuantity());
            ebf.setBouquet(ebi);
            price[0] += ebf.getFlowers().getAvgPrice();
            return ebf;
        }).collect(Collectors.toCollection(ArrayList::new));
        if (ebfs.stream().noneMatch(Objects::isNull)) return null;
        ebi.getItems().addAll(ebfs);
        bill.setTotalPrice(price[0]);
        ebi.setBill(bill);
        bill.setCreatedAt(OffsetDateTime.now());
        return ebi;
    }

    public EntityBouquetItem ctoa(CreateBouquetItem creater) {
        EntityBouquetItem ebi = new EntityBouquetItem();
        double price = 0;
        for (CreateBouquetFlower cbf : creater.getItems()) {

            EntityBouquetFlower ebf = new EntityBouquetFlower();

            EntityFlower flower;
            try {
                flower = service.getById(cbf.getFlowerId());
            } catch (EntityNotFoundException | ObjectNotFoundException e) {
                log.error("e: ", e);
                return null;
            }

            ebf.setFlowers(flower);
            ebf.setBouquet(ebi);
            if (cbf.getQuantity() <= flower.getQuantity()) {
                ebf.setQuantity(cbf.getQuantity());
            } else {
                return null;
            }

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
        for (EntityBouquetFlower ebf : source.getItems()) {
            BouquetFlower bf = new BouquetFlower();

            Flower f = service.btoa(ebf.getFlowers());
            bf.setFlower(f);
            bf.setQuantity(ebf.getQuantity());
            bf.setId(ebf.getId());
            bi.addItemsItem(bf);
        }
        bi.setBillId(source.getBill().getId());
        bi.setId(source.getId());
        return bi;

    }


    public EntityBouquetItem getByModel(BouquetItem bi) {
        assert bi.getId() != null;
        return repo.getReferenceById(bi.getId());
    }
}
