package com.ludenedev.flowershop.adapter.mysql.repositories;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlowerRepository extends JpaRepository<EntityFlower, UUID> {
}
