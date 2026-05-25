package com.ludenedev.flowershop.demo.controller;

import com.ludenedev.flowershop.demo.service.DemoCreationService;
import com.ludenedev.flowershop.demo.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Profile("demo")
@RestController
@AllArgsConstructor
public class DemoController {

    private final JwtService service;
    private final DemoCreationService demoCreationService;

    @PostMapping("/api/demo/start")
    public String startDemo(){
        String token = service.createToken();
        String sessionid = service.extractSessionId(token);
        try{

        demoCreationService.createSession(sessionid);
        }catch (Exception e){
            System.out.println(e);
        }
        return token;
    }



    @GetMapping("/api/config")
    public Map<String, String> config() {
        return Map.of("mode", "demo");
    }


}
