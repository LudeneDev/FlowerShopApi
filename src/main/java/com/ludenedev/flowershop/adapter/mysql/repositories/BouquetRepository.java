package com.ludenedev.flowershop.adapter.mysql.repositories;


import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BouquetRepository extends JpaRepository<EntityBouquetItem, UUID> {
}
