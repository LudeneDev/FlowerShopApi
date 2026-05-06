package com.ludenedev.flowershop.demo.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.demo.DemoContext;
import com.ludenedev.flowershop.demo.entities.DemoEntityFlower;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import com.ludenedev.flowershop.demo.repositories.DemoFlowerRepository;
import com.ludenedev.flowershop.demo.repositories.DemoSessionRepository;
import com.ludenedev.flowershop.service.providers.FlowerProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
@Profile("demo")
public class DemoFlowerProviderImpl implements FlowerProvider{

    private final DemoFlowerRepository demoFlowerRepository;
    private final DemoContext ctx;
    private final DemoSessionRepository demoSessionRepository;

    @Override
    public List<EntityFlower> getAll() {
        return demoSessionRepository
                .getSessionBySessionId(ctx.getSessionId())
                .orElseThrow()
                .getFlowers()
                .stream()
                .map(DemoEntityFlower::getFlower)
                .toList();
    }

    @Override
    public void afterCreate(EntityFlower saved) {
        DemoEntitySession des = demoSessionRepository.getSessionBySessionId(ctx.getSessionId()).orElseThrow();
        DemoEntityFlower def = new DemoEntityFlower();
        def.setSession(des);
        def.setFlower(saved);
        demoFlowerRepository.save(def);
    }

    @Override
    public void checkForSession(List<UUID> ids) {
        List<DemoEntityFlower> flowers = demoSessionRepository.getSessionBySessionId(ctx.getSessionId()).orElseThrow().getFlowers();
        for(UUID id : ids){
            flowers.stream().filter(x -> x.getFlower().getId().equals(id))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
