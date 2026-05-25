package com.ludenedev.flowershop.demo.repositories;

import com.ludenedev.flowershop.demo.entities.DemoEntitySession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemoSessionRepository extends JpaRepository<DemoEntitySession, UUID> {

    Optional<DemoEntitySession> getSessionBySessionId(String sessionId);


    @Query("SELECT s FROM DemoEntitySession s WHERE :now > s.expire")
    Optional<List<DemoEntitySession>> findByExpire(@Param("now") Instant now);
}


