package com.ludenedev.flowers.flowers.controller;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowers.flowers.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowers.flowers.api.BouquetsApi;
import com.ludenedev.flowers.flowers.model.Bill;
import com.ludenedev.flowers.flowers.model.BouquetItem;
import com.ludenedev.flowers.flowers.model.CreateBouquetItem;
import com.ludenedev.flowers.flowers.service.BouquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class BouquetController extends BouquetService implements BouquetsApi {
    @Autowired
    BouquetRepository bouquetRepository;


    @Override
    public ResponseEntity<List<BouquetItem>> bouquetsGet() {
        List<BouquetItem> items = bouquetRepository.findAll().stream().map(this::btoa).toList();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Bill> bouquetsPost(CreateBouquetItem createBouquetItem) {
        EntityBouquetItem ebi = this.ctoa(createBouquetItem);
        if (Objects.isNull(ebi)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        ebi = bouquetRepository.saveAndFlush(ebi);

        Bill bill = new Bill();
        bill.addItemsItem(this.btoa(ebi));
        bill.setTotalPrice(ebi.getBill().getTotalPrice());
        bill.setCreatedAt(ebi.getBill().getCreatedAt());
        bill.setId(ebi.getBill().getId());
        return new ResponseEntity<>(bill, HttpStatus.CREATED);

    }
}
