package com.ludenedev.flowershop.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile("demo")
@EnableScheduling
@Configuration
public class SchedulerConfig {

}
