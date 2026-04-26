package com.ludenedev.flowershop.adapter.mysql.entities;

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
    @Column(name = "kind", nullable = false)
    String kind;
    @Column(name = "quantity", nullable = false)
    Integer quantity;
    @Column(nullable = false)
    Double avgPrice;

}