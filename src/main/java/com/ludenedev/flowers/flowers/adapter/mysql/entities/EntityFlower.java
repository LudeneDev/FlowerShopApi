package com.ludenedev.flowers.flowers.adapter.mysql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "flower")
@Table(name = "flower")
public class EntityFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(name = "kind")
    String kind;
    @Column(name = "quantity")
    Integer quantity;

    Double avgPrice;

}