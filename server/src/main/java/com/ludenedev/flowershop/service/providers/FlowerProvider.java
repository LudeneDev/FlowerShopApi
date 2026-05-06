package com.ludenedev.flowershop.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;

import java.util.List;
import java.util.UUID;

public interface FlowerProvider {


    List<EntityFlower> getAll();

    default void afterCreate(EntityFlower saved) {};

    default void checkForSession(List<UUID> ids) {};



}
