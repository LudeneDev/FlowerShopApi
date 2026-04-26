package com.ludenedev.flowershop.controller;

import com.ludenedev.flowershop.api.FlowersApi;
import com.ludenedev.flowershop.model.CreateFlower;
import com.ludenedev.flowershop.model.Flower;
import com.ludenedev.flowershop.service.FlowersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FlowerController implements FlowersApi {

    private final FlowersService flowersService;


    @Override
    public ResponseEntity<List<Flower>> flowersGet() {
        List<Flower> flowers = flowersService.getAllFlowers();
        return new ResponseEntity<>(flowers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Flower> flowersPost(CreateFlower createFlower) {
        if(createFlower.getAvgPrice() <= 0 || createFlower.getQuantity() <= 0 || createFlower.getKind().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Flower createdFlower = flowersService.createFlower(createFlower);

        return new ResponseEntity<>(createdFlower, HttpStatus.CREATED);
    }
}