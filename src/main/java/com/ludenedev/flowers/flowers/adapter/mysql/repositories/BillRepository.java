package com.ludenedev.flowers.flowers.adapter.mysql.repositories;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillRepository extends JpaRepository<EntityBill, UUID> {
}
