package com.ludenedev.flowershop.controller;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.api.BouquetsApi;
import com.ludenedev.flowershop.model.Bill;
import com.ludenedev.flowershop.model.BouquetItem;
import com.ludenedev.flowershop.model.CreateBouquetItem;
import com.ludenedev.flowershop.service.BouquetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BouquetController implements BouquetsApi {

    private final BouquetService bouquetService;


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