package com.ludenedev.flowershop.prod.service.providers;

import com.ludenedev.flowershop.prod.mysql.entities.EntityBouquetItem;

import java.util.List;
import java.util.UUID;

public interface BouquetProvider {


    List<EntityBouquetItem> getAll();

    default void afterCreate(EntityBouquetItem saved) {}

    default void checkForSession(List<UUID> ids) {}



}
