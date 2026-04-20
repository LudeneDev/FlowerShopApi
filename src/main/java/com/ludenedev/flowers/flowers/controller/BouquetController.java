package com.ludenedev.flowers.flowers.controller;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowers.flowers.api.BouquetsApi;
import com.ludenedev.flowers.flowers.model.Bill;
import com.ludenedev.flowers.flowers.model.BouquetItem;
import com.ludenedev.flowers.flowers.model.CreateBouquetItem;
import com.ludenedev.flowers.flowers.service.BouquetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BouquetController implements BouquetsApi {

    private final BouquetService bouquetService;

    public BouquetController(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    @Override
    public ResponseEntity<List<BouquetItem>> bouquetsGet() {
        List<BouquetItem> items = bouquetService.getAllBouquets();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Bill> bouquetsPost(CreateBouquetItem createBouquetItem) {
        try {
            EntityBouquetItem createdBouquet = bouquetService.createBouquet(createBouquetItem);

            Bill bill = new Bill();
            bill.setId(createdBouquet.getBill().getId());
            bill.setTotalPrice(createdBouquet.getBill().getTotalPrice());
            bill.addItemsItem(bouquetService.btoa(createdBouquet));

            return new ResponseEntity<>(bill, HttpStatus.CREATED);

        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}