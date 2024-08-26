package com.testing.slidingpuzzle.config;

import com.testing.slidingpuzzle.enums.MoveDirection;
import com.testing.slidingpuzzle.service.strategy.MoveStrategy;
import com.testing.slidingpuzzle.service.strategy.impl.MoveDownStrategyImpl;
import com.testing.slidingpuzzle.service.strategy.impl.MoveLeftStrategyImpl;
import com.testing.slidingpuzzle.service.strategy.impl.MoveRightStrategyImpl;
import com.testing.slidingpuzzle.service.strategy.impl.MoveUpStrategyImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class MoveStrategiesConfig {

    private final MoveUpStrategyImpl moveUpStrategy;
    private final MoveDownStrategyImpl moveDownStrategy;
    private final MoveLeftStrategyImpl moveLeftStrategy;
    private final MoveRightStrategyImpl moveRightStrategy;

    @Bean
    public Map<MoveDirection, MoveStrategy> moveStrategies() {
        Map<MoveDirection, MoveStrategy> moveStrategies = new EnumMap<>(MoveDirection.class);
        moveStrategies.put(MoveDirection.UP, moveUpStrategy);
        moveStrategies.put(MoveDirection.DOWN, moveDownStrategy);
        moveStrategies.put(MoveDirection.LEFT, moveLeftStrategy);
        moveStrategies.put(MoveDirection.RIGHT, moveRightStrategy);
        return moveStrategies;
    }
}
