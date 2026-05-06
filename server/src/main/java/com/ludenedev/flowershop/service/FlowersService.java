package com.ludenedev.flowershop.service;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowershop.demo.DemoContext;
import com.ludenedev.flowershop.demo.entities.DemoEntityFlower;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import com.ludenedev.flowershop.demo.repositories.DemoFlowerRepository;
import com.ludenedev.flowershop.demo.repositories.DemoSessionRepository;
import com.ludenedev.flowershop.model.CreateFlower;
import com.ludenedev.flowershop.model.Flower;
import com.ludenedev.flowershop.service.providers.FlowerProvider;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FlowersService implements MySqlAdapterMapper<EntityFlower, Flower> {

    private final FlowerRepository flowerRepository;
    private final FlowerProvider provider;


    public List<Flower> getAllFlowers() {

        return provider.getAll().stream()
                .map(this::btoa)
                .toList();
    }
    @Transactional
    public void updateQuantity(UUID id, int quantity){
        provider.checkForSession(List.of(id));
        EntityFlower ef = flowerRepository.getReferenceById(id);
        ef.setQuantity(quantity);
        flowerRepository.save(ef);
    }
    @Transactional
    public Flower createFlower(CreateFlower createFlower) {
        EntityFlower entity = this.ctoa(createFlower);
        EntityFlower saved = flowerRepository.save(entity);
        provider.afterCreate(saved);
        return this.btoa(saved);
    }



    @Override
    public EntityFlower atob(Flower source) {
        EntityFlower ef = new EntityFlower();
        if (Objects.nonNull(source.getId())) {
            provider.checkForSession(List.of(source.getId()));
            flowerRepository.getReferenceById(source.getId());
        }
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
        provider.checkForSession(List.of(id));
        return flowerRepository.getReferenceById(id);
    }
}