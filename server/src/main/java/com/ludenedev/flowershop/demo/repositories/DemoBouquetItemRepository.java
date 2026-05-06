package com.ludenedev.flowershop.demo.repositories;

import com.ludenedev.flowershop.demo.entities.DemoEntityBouquetItem;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemoBouquetItemRepository extends JpaRepository<DemoEntityBouquetItem, UUID> {

    Optional<List<DemoEntityBouquetItem>> findBySession(DemoEntitySession session);
}
