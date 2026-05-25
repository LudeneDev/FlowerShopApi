package com.ludenedev.flowershop.prod.service.providers;

import com.ludenedev.flowershop.prod.mysql.entities.EntityBill;

import java.util.List;
import java.util.UUID;

public interface BillProvider {

    List<EntityBill> getAll();

    default void afterCreate() {};

    default void checkForSession(List<UUID> ids) {};
}
