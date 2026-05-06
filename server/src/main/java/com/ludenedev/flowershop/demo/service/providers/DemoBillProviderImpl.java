package com.ludenedev.flowershop.demo.service.providers;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.demo.DemoContext;
import com.ludenedev.flowershop.demo.entities.DemoEntityBill;
import com.ludenedev.flowershop.demo.repositories.DemoSessionRepository;
import com.ludenedev.flowershop.service.providers.BillProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Profile("demo")
@Service
public class DemoBillProviderImpl implements BillProvider {

    private final DemoContext ctx;
    private final DemoSessionRepository demoSessionRepository;

    @Override
    public List<EntityBill> getAll() {
        return  demoSessionRepository.getSessionBySessionId(ctx.getSessionId())
                .orElseThrow()
                .getBills()
                .stream()
                .map(DemoEntityBill::getBill)
                .toList();
    }

    @Override
    public void checkForSession(List<UUID> ids) {
        List<DemoEntityBill> bills = demoSessionRepository.getSessionBySessionId(ctx.getSessionId()).orElseThrow().getBills();
        for(UUID id : ids){
           bills.stream().filter(x -> x.getBill().getId().equals(id))
                   .findFirst()
                   .orElseThrow();
        }
    }
}
