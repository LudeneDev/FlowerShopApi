package com.ludenedev.flowershop.demo;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.FlowerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@TestConfiguration
public class DemoTestConfig {

    public static EntityFlower flower = new EntityFlower();

    @Bean
    CommandLineRunner initData(FlowerRepository flowerRepository) {
        return args -> {
            fillFlower();

            flower = flowerRepository.save(flower);
        };
    }

    private void fillFlower(){
        flower.setKind("ROSE");
        flower.setQuantity(100);
        flower.setAvgPrice(10D);
    }
}