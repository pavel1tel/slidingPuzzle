package com.testing.slidingpuzzle.service.strategy.impl;

import com.testing.slidingpuzzle.model.GameModel;
import com.testing.slidingpuzzle.service.strategy.MoveStrategy;

public class MoveRightStrategyImpl implements MoveStrategy {

    @Override
    public GameModel move(GameModel gameModel) {
        System.out.println("Move Right");
        return gameModel;
    }
}
