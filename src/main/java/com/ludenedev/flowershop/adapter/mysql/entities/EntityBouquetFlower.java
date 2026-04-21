package com.ludenedev.flowershop.adapter.mysql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EntityBouquetFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @ManyToOne
    @JoinColumn(name = "flower_id")
    EntityFlower flowers;
    @ManyToOne
    @JoinColumn(name = "bouquet_item_id")
    EntityBouquetItem bouquet;
    Integer quantity;

}
