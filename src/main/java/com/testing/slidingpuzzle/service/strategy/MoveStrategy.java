package com.testing.slidingpuzzle.service.strategy;

import com.testing.slidingpuzzle.model.GameModel;

public interface MoveStrategy {
    GameModel move(GameModel gameModel);
}
