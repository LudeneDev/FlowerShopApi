package com.ludenedev.flowershop.adapter.mysql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "bouquet_item")
@Table(name = "bouquet_item")
public class EntityBouquetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @OneToMany(mappedBy = "bouquet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EntityBouquetFlower> items = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id")
    private EntityBill bill;


}
