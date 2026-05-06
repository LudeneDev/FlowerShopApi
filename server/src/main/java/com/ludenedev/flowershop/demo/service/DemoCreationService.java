package com.ludenedev.flowershop.demo.service;


import com.ludenedev.flowershop.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetFlower;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import com.ludenedev.flowershop.adapter.mysql.repositories.BillRepository;
import com.ludenedev.flowershop.adapter.mysql.repositories.BouquetRepository;
import com.ludenedev.flowershop.adapter.mysql.repositories.FlowerRepository;
import com.ludenedev.flowershop.demo.entities.DemoEntityBill;
import com.ludenedev.flowershop.demo.entities.DemoEntityBouquetItem;
import com.ludenedev.flowershop.demo.entities.DemoEntityFlower;
import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import com.ludenedev.flowershop.demo.repositories.DemoBillRepository;
import com.ludenedev.flowershop.demo.repositories.DemoBouquetItemRepository;
import com.ludenedev.flowershop.demo.repositories.DemoFlowerRepository;
import com.ludenedev.flowershop.demo.repositories.DemoSessionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Profile("demo")
@AllArgsConstructor
@Service
public class DemoCreationService {


    private final DemoBouquetItemRepository demoBouquetItemRepository;
    private final BouquetRepository bouquetRepository;
    private final DemoFlowerRepository demoFlowerRepository;
    private final FlowerRepository flowerRepository;
    private final DemoBillRepository demoBillRepository;
    private final BillRepository billRepository;
    private final DemoSessionRepository demoSessionRepository;

    @Transactional
    public void createSession(String sessionId) {
        if(demoSessionRepository.getSessionBySessionId(sessionId).isPresent()) return;

        DemoEntitySession des = new DemoEntitySession();
        des.setSessionId(sessionId);
        des.setExpire(Instant.now().plusSeconds(600));

        demoSessionRepository.save(des);
        // ---------- 1) Clone Flowers ----------
        var existingDemoFlowers = demoFlowerRepository.findBySession(des);
        if (!existingDemoFlowers.get().isEmpty()) return;

        var baseFlowers = flowerRepository.findAll();
        baseFlowers = baseFlowers.stream().filter(x -> demoFlowerRepository.findAll().stream().noneMatch(y -> y.getFlower().getId().equals(x.getId()))).toList();

        Map<UUID, EntityFlower> flowerMap = new HashMap<>();

        for (EntityFlower original : baseFlowers) {

            EntityFlower clone = new EntityFlower();
            clone.setQuantity(original.getQuantity());
            clone.setAvgPrice(original.getAvgPrice());
            clone.setKind(original.getKind());

            clone = flowerRepository.save(clone);

            DemoEntityFlower def = new DemoEntityFlower();
            def.setSession(des);
            def.setFlower(clone);
            demoFlowerRepository.save(def);

            flowerMap.put(original.getId(), clone);
        }

        // ---------- 2) Clone Bouquet Items ----------
        var existingDemoItems = demoBouquetItemRepository.findBySession(des);
        if (!existingDemoItems.get().isEmpty()) return;

        var baseItems = bouquetRepository.findAll();

        baseItems = baseItems.stream().filter(x -> demoBouquetItemRepository.findAll().stream().noneMatch(y -> y.getItem().getId().equals(x.getId()))).toList();
        Map<UUID, EntityBouquetItem> itemMap = new HashMap<>();

        for (EntityBouquetItem originalItem : baseItems) {

            EntityBouquetItem newItem = new EntityBouquetItem();
            newItem = bouquetRepository.save(newItem);

            for (EntityBouquetFlower originalFlower : originalItem.getItems()) {

                EntityFlower mappedFlower = flowerMap.get(originalFlower.getFlowers().getId());
                if (mappedFlower == null) {
                    throw new IllegalStateException("Missing flower mapping");
                }

                EntityBouquetFlower newFlower = new EntityBouquetFlower();
                newFlower.setBouquet(newItem);
                newFlower.setFlowers(mappedFlower);
                newFlower.setQuantity(originalFlower.getQuantity());

                newItem.getItems().add(newFlower);
            }

            itemMap.put(originalItem.getId(), newItem);
        }

        // ---------- 3) Clone Bills ----------
        var existingDemoBills = demoBillRepository.findBySession(des);
        if (!existingDemoBills.get().isEmpty()) return;

        var baseBills = billRepository.findAll();

        baseBills = baseBills.stream().filter(x -> demoBillRepository.findAll().stream().noneMatch(y -> y.getBill().getId().equals(x.getId()))).toList();

        for (EntityBill originalBill : baseBills) {

            EntityBill newBill = new EntityBill();
            newBill.setCreatedAt(originalBill.getCreatedAt());
            newBill.setTotalPrice(originalBill.getTotalPrice());

            for (EntityBouquetItem originalItem : originalBill.getItems()) {

                EntityBouquetItem mappedItem = itemMap.get(originalItem.getId());
                if (mappedItem == null) {
                    throw new IllegalStateException("Missing bouquet item mapping");
                }

                newBill.getItems().add(mappedItem);
            }

            newBill = billRepository.save(newBill);

            DemoEntityBill demoBill = new DemoEntityBill();
            demoBill.setBill(newBill);
            demoBill.setSession(des);
            demoBillRepository.save(demoBill);

            for (EntityBouquetItem item : newBill.getItems()) {
                DemoEntityBouquetItem demoItem = new DemoEntityBouquetItem();
                demoItem.setItem(item);
                demoItem.setSession(des);
                demoBouquetItemRepository.save(demoItem);
            }
        }
    }
}
