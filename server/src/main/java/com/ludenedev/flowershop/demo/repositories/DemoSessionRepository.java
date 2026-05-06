package com.ludenedev.flowershop.demo.repositories;

import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DemoSessionRepository extends JpaRepository<DemoEntitySession, UUID> {

    Optional<DemoEntitySession> getSessionBySessionId(String sessionId);
}
