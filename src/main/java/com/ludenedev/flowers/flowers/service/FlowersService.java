package com.ludenedev.flowers.flowers.service;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowers.flowers.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowers.flowers.model.CreateFlower;
import com.ludenedev.flowers.flowers.model.Flower;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.UUID;

public class FlowersService implements MySqlAdapterMapper<EntityFlower, Flower> {

    @Autowired
    FlowerRepository repo;

    @Override
    public EntityFlower atob(Flower source) {
        EntityFlower ef = new EntityFlower();
        if (Objects.nonNull(source.getId())) return repo.getReferenceById(source.getId());
        ef.setKind(source.getKind());
        ef.setAvgPrice(source.getAvgPrice());
        ef.setQuantity(source.getQuantity());

        return ef;
    }

    public EntityFlower ctoa(CreateFlower source) {
        EntityFlower flower = new EntityFlower();
        flower.setKind(source.getKind());
        flower.setQuantity(source.getQuantity());
        flower.setAvgPrice(source.getAvgPrice());
        return flower;
    }

    @Override
    public Flower btoa(EntityFlower source) {
        Flower flower = new Flower();
        flower.setQuantity(source.getQuantity());
        flower.setId(source.getId());
        flower.setKind(source.getKind());
        flower.setAvgPrice(source.getAvgPrice());
        return flower;
    }


    public EntityFlower getById(UUID id) throws EntityNotFoundException, ObjectNotFoundException {
        return repo.getReferenceById(id);
    }
}
