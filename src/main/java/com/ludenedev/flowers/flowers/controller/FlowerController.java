package com.ludenedev.flowers.flowers.controller;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowers.flowers.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowers.flowers.api.FlowersApi;
import com.ludenedev.flowers.flowers.model.CreateFlower;
import com.ludenedev.flowers.flowers.model.Flower;
import com.ludenedev.flowers.flowers.service.FlowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class FlowerController extends FlowersService implements FlowersApi {
    @Autowired
    FlowerRepository flowerRepo;


    @Override
    public ResponseEntity<List<Flower>> flowersGet() {
        List<Flower> l = flowerRepo.findAll().stream().map(this::btoa).toList();
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Flower> flowersPost(CreateFlower createFlower) {
        EntityFlower flower = flowerRepo.save(this.ctoa(createFlower));
        if (Objects.isNull(flower.getId())) return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
