package com.ludenedev.flowershop.demo.repositories;

import com.ludenedev.flowershop.demo.entities.DemoEntityBill;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemoBillRepository extends JpaRepository<DemoEntityBill, UUID> {
    Optional<List<DemoEntityBill>> findBySession(DemoEntitySession session);
}
