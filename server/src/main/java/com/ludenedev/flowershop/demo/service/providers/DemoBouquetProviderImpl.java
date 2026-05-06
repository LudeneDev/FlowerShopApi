package com.ludenedev.flowershop.demo.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.demo.DemoContext;
import com.ludenedev.flowershop.demo.entities.DemoEntityBill;
import com.ludenedev.flowershop.demo.entities.DemoEntityBouquetItem;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import com.ludenedev.flowershop.demo.repositories.DemoBillRepository;
import com.ludenedev.flowershop.demo.repositories.DemoBouquetItemRepository;
import com.ludenedev.flowershop.demo.repositories.DemoSessionRepository;
import com.ludenedev.flowershop.service.providers.BouquetProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Profile("demo")

public class DemoBouquetProviderImpl implements BouquetProvider {


   private final DemoSessionRepository demoSessionRepository;
    private final DemoContext ctx;
    private final DemoBouquetItemRepository demoBouquetItemRepository;
    private final DemoBillRepository demoBillRepository;

    @Override
    public List<EntityBouquetItem> getAll() {
        return demoSessionRepository.getSessionBySessionId(ctx.getSessionId())
                .orElseThrow()
                .getItems()
                .stream()
                .map(DemoEntityBouquetItem::getItem)
                .toList();
    }

    @Override
    public void afterCreate(EntityBouquetItem ebi) {
        DemoEntitySession des = demoSessionRepository.getSessionBySessionId(ctx.getSessionId())
                .orElseThrow();
        DemoEntityBouquetItem debi = new DemoEntityBouquetItem();
        debi.setSession(des);
        debi.setItem(ebi);
        demoBouquetItemRepository.save(debi);
        DemoEntityBill deb = new DemoEntityBill();
        deb.setSession(des);
        deb.setBill(ebi.getBill());
        demoBillRepository.save(deb);
    }

    @Override
    public void checkForSession(List<UUID> ids) {
        List<DemoEntityBouquetItem> items = demoSessionRepository.getSessionBySessionId(ctx.getSessionId()).orElseThrow().getItems();
        for(UUID id : ids){
            items.stream().filter(x -> x.getItem().getId().equals(id))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
