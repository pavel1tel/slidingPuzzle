package com.testing.slidingpuzzle.service.strategy.impl;

import com.testing.slidingpuzzle.model.GameModel;
import com.testing.slidingpuzzle.service.strategy.MoveStrategy;

public class MoveLeftStrategyImpl implements MoveStrategy {

    @Override
    public GameModel move(GameModel gameModel) {
        System.out.println("Move Left");
        return gameModel;
    }
}
