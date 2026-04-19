package com.ludenedev.flowers.flowers.service;

import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBill;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetFlower;
import com.ludenedev.flowers.flowers.adapter.mysql.entities.EntityBouquetItem;
import com.ludenedev.flowers.flowers.model.Bill;
import com.ludenedev.flowers.flowers.model.BouquetItem;
import org.springframework.beans.factory.annotation.Autowired;

public class BillService implements MySqlAdapterMapper<EntityBill, Bill> {

    @Autowired
    BouquetService service;


    @Override
    public EntityBill atob(Bill source) {
        EntityBill bill = new EntityBill();
        bill.setCreatedAt(source.getCreatedAt());
        double price = 0;
        for (BouquetItem bi : source.getItems()) {
            EntityBouquetItem ebi = service.getByModel(bi);
            for (EntityBouquetFlower ebf : ebi.getItems()) {
                price += (ebf.getQuantity() * ebf.getFlowers().getAvgPrice());
            }
            bill.getItems().add(ebi);
        }
        bill.setTotalPrice(price);
        return bill;
    }

    @Override
    public Bill btoa(EntityBill source) {
        Bill bill = new Bill();
        bill.setCreatedAt(source.getCreatedAt());
        bill.setTotalPrice(source.getTotalPrice());
        bill.setId(source.getId());
        for (EntityBouquetItem ebi : source.getItems()) {
            bill.addItemsItem(service.btoa(ebi));
        }
        return bill;
    }
}
