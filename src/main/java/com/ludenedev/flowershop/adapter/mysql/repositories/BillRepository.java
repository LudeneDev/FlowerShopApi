package com.ludenedev.flowershop.adapter.mysql.repositories;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillRepository extends JpaRepository<EntityBill, UUID> {
}
