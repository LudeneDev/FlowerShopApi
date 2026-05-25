package com.ludenedev.flowershop.prod.mysql.repositories;

import com.ludenedev.flowershop.prod.mysql.entities.EntityFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlowerRepository extends JpaRepository<EntityFlower, UUID> {
}
