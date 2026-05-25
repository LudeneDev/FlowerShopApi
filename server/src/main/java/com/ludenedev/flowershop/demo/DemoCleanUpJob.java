package com.ludenedev.flowershop.demo;

import com.ludenedev.flowershop.demo.repositories.DemoSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Profile("demo")
@Component
@RequiredArgsConstructor
public class DemoCleanUpJob {

    private final DemoSessionRepository repository;

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void cleanupExpiredSessions() {
        int before = repository.findAll().size();
        var sessions = repository.findByExpire(Instant.now());

        sessions.ifPresent(repository::deleteAll);


        int after = repository.findAll().size();

        System.out.println("Demo cleanup executed: " + before + " → " + after);
    }



}
