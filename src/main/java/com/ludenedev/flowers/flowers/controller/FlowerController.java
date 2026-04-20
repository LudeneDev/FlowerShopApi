package com.ludenedev.flowers.flowers.controller;

import com.ludenedev.flowers.flowers.api.FlowersApi;
import com.ludenedev.flowers.flowers.model.CreateFlower;
import com.ludenedev.flowers.flowers.model.Flower;
import com.ludenedev.flowers.flowers.service.FlowersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlowerController implements FlowersApi {

    private final FlowersService flowersService;

    public FlowerController(FlowersService flowersService) {
        this.flowersService = flowersService;
    }

    @Override
    public ResponseEntity<List<Flower>> flowersGet() {
        List<Flower> flowers = flowersService.getAllFlowers();
        return new ResponseEntity<>(flowers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Flower> flowersPost(CreateFlower createFlower) {
        Flower createdFlower = flowersService.createFlower(createFlower);

        return new ResponseEntity<>(createdFlower, HttpStatus.CREATED);
    }
}