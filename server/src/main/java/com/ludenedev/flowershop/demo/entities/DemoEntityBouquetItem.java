package com.ludenedev.flowershop.demo.entities;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityBouquetItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("demo")
@Entity
@Getter
@Setter
@Service

public class DemoEntityBouquetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    DemoEntitySession session;

    @OneToOne
    EntityBouquetItem item;
}
