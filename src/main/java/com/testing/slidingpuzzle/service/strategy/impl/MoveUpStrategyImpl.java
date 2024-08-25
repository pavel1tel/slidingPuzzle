package com.testing.slidingpuzzle.service.strategy.impl;

import com.testing.slidingpuzzle.model.GameModel;
import com.testing.slidingpuzzle.service.strategy.MoveStrategy;
import lombok.extern.java.Log;

@Log
public class MoveUpStrategyImpl implements MoveStrategy {

    @Override
    public GameModel move(GameModel gameModel) {
        log.info("Move up strategy");
        return gameModel;
    }
}
