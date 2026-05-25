package com.ludenedev.flowershop.prod.service.providers;

import com.ludenedev.flowershop.prod.mysql.entities.EntityFlower;

import java.util.List;
import java.util.UUID;

public interface FlowerProvider {


    List<EntityFlower> getAll();

    default void afterCreate(EntityFlower saved) {};

    default void checkForSession(List<UUID> ids) {};



}
