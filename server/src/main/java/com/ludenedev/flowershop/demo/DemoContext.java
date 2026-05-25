package com.ludenedev.flowershop.demo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Profile("demo")
@Component

public class DemoContext {
    private static final ThreadLocal<String> sessionIdHolder = new ThreadLocal<>();

    public String getSessionId(){
        return sessionIdHolder.get();
    }

    public void setSessionId(String sessionId){
        sessionIdHolder.set(sessionId);
    }

    public boolean isDemo(){
        return sessionIdHolder.get() != null;
    }

    public void clear(){
        sessionIdHolder.remove();
    }
}
