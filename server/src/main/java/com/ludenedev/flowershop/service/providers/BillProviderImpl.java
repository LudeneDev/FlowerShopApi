package com.ludenedev.flowershop.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.repositories.BillRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Profile("!demo")
@Service
public class BillProviderImpl implements BillProvider{

    private final BillRepository repo;
    @Override
    public List<EntityBill> getAll() {
        return repo.findAll();
    }
}
