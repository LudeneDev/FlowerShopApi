package com.ludenedev.flowers.flowers.adapter.mysql.repositories;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlowerRepository extends JpaRepository<EntityFlower, UUID> {
}
