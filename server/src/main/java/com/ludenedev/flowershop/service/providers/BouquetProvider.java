package com.ludenedev.flowershop.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;

import java.util.List;
import java.util.UUID;

public interface BouquetProvider {


    List<EntityBouquetItem> getAll();

    default void afterCreate(EntityBouquetItem saved) {}

    default void checkForSession(List<UUID> ids) {}



}
