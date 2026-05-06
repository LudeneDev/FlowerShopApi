package com.ludenedev.flowershop.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowershop.demo.entities.DemoEntityBill;
import com.ludenedev.flowershop.demo.entities.DemoEntityBouquetItem;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Profile("!demo")
public class BouquetProviderImpl implements BouquetProvider{

    private final BouquetRepository repo;

    @Override
    public List<EntityBouquetItem> getAll() {
        return repo.findAll();
    }


}
