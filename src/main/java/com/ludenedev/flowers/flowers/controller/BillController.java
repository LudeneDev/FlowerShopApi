package com.ludenedev.flowers.flowers.controller;

import com.ludenedev.flowers.flowers.api.BillsApi;
import com.ludenedev.flowers.flowers.model.Bill;
import com.ludenedev.flowers.flowers.service.BillService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class BillController implements BillsApi {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @Override
    public ResponseEntity<List<Bill>> billsGet() {
        List<Bill> bills = billService.getAllBills();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Bill> billsIdGet(UUID id) {
        try {
            Bill bill = billService.getBillById(id);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error("Error fetching bill: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}