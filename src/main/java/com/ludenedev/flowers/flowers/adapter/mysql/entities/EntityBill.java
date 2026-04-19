package com.ludenedev.flowers.flowers.adapter.mysql.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "bill")
@Entity
public class EntityBill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToMany(mappedBy = "bill")
    private Set<EntityBouquetItem> items = new HashSet<>();

    private Double totalPrice;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;


}
