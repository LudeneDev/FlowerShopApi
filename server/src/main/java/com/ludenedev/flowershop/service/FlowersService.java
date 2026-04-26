package com.ludenedev.flowershop.service;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowershop.model.CreateFlower;
import com.ludenedev.flowershop.model.Flower;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FlowersService implements MySqlAdapterMapper<EntityFlower, Flower> {

    private final FlowerRepository repo;

    public FlowersService(FlowerRepository repo) {
        this.repo = repo;
    }

    public List<Flower> getAllFlowers() {
        return repo.findAll().stream()
                .map(this::btoa)
                .toList();
    }
    @Transactional
    public void updateQuantity(UUID id, int quantity){
        EntityFlower ef = repo.getReferenceById(id);
        ef.setQuantity(quantity);
        repo.save(ef);
    }
    @Transactional
    public Flower createFlower(CreateFlower createFlower) {
        EntityFlower entity = this.ctoa(createFlower);
        EntityFlower saved = repo.save(entity);
        return this.btoa(saved);
    }



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

    public EntityFlower getById(UUID id) throws EntityNotFoundException {
        return repo.getReferenceById(id);
    }
}