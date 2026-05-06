package com.ludenedev.flowershop.demo.repositories;

import com.ludenedev.flowershop.demo.entities.DemoEntityFlower;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemoFlowerRepository extends JpaRepository<DemoEntityFlower, UUID> {
    Optional<List<DemoEntityFlower>> findBySession(DemoEntitySession session);
}
