package com.ludenedev.flowershop.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Profile("demo")

@Entity

@Table(
        indexes = {
                @Index(name = "idx_demo_session_expire", columnList = "expire"),
                @Index(name = "idx_demo_session_session_id", columnList = "sessionId")
        }
)
@Getter
@Setter
public class DemoEntitySession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String sessionId;

    Instant expire;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DemoEntityFlower> flowers;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DemoEntityBouquetItem> items;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DemoEntityBill> bills;

}
