package com.ludenedev.flowershop.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;

import java.util.List;
import java.util.UUID;

public interface BillProvider {

    List<EntityBill> getAll();

    default void afterCreate() {};

    default void checkForSession(List<UUID> ids) {};
}
