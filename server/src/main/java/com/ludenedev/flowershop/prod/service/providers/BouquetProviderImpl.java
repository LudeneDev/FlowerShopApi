package com.ludenedev.flowershop.prod.service.providers;

import com.ludenedev.flowershop.prod.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.prod.mysql.repositories.BouquetRepository;
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
