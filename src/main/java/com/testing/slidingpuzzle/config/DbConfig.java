package com.testing.slidingpuzzle.config;

import com.testing.slidingpuzzle.model.GameModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DbConfig {

    @Bean
    @SessionScope
    public Map<Long, GameModel> games(){
        return new HashMap<>();
    }
}
