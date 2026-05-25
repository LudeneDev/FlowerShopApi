package com.ludenedev.flowershop.prod.mysql.repositories;


import com.ludenedev.flowershop.prod.mysql.entities.EntityBouquetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BouquetRepository extends JpaRepository<EntityBouquetItem, UUID> {
}
