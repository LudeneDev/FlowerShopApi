package com.ludenedev.flowershop.demo.entities;

import com.ludenedev.flowershop.adapter.mysql.entities.EntityFlower;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Profile("demo")
@Entity
@Getter
@Setter
public class DemoEntityFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    DemoEntitySession session;

    @OneToOne
    @JoinColumn(name = "flower_id")
    EntityFlower flower;

}
