package com.ludenedev.flowers.flowers.controller;

import com.ludenedev.flowers.flowers.adapter.mysql.repositories.BillRepository;
import com.ludenedev.flowers.flowers.api.BillsApi;
import com.ludenedev.flowers.flowers.model.Bill;
import com.ludenedev.flowers.flowers.service.BillService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class BillController extends BillService implements BillsApi {
    @Autowired
    BillRepository repo;


    @Override
    public ResponseEntity<List<Bill>> billsGet() {
        List<Bill> l = repo.findAll().stream().map(this::btoa).toList();
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Bill> billsIdGet(UUID id) {
        try {

            Bill b = this.btoa(repo.getReferenceById(id));
            return new ResponseEntity<>(b, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            log.error("There is no bill with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
