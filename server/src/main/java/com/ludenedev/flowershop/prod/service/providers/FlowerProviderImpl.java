package com.ludenedev.flowershop.prod.service.providers;

import com.ludenedev.flowershop.prod.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.prod.mysql.repositories.FlowerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Profile("!demo")
@Service
public class FlowerProviderImpl implements FlowerProvider{


    private final FlowerRepository flowerRepository;


    @Override
    public List<EntityFlower> getAll() {
        return flowerRepository.findAll();
    }
}
