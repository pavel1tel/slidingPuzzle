package com.testing.slidingpuzzle.enums;

import com.testing.slidingpuzzle.service.strategy.MoveStrategy;
import com.testing.slidingpuzzle.service.strategy.impl.MoveDownStrategyImpl;
import com.testing.slidingpuzzle.service.strategy.impl.MoveLeftStrategyImpl;
import com.testing.slidingpuzzle.service.strategy.impl.MoveRightStrategyImpl;
import com.testing.slidingpuzzle.service.strategy.impl.MoveUpStrategyImpl;

public enum MoveDirection {
    UP {
        @Override
        public MoveStrategy getStrategy() {
            return new MoveUpStrategyImpl();
        }
    }, DOWN {
        @Override
        public MoveStrategy getStrategy() {
            return new MoveDownStrategyImpl();
        }
    }, LEFT {
        @Override
        public MoveStrategy getStrategy() {
            return new MoveLeftStrategyImpl();
        }
    }, RIGHT {
        @Override
        public MoveStrategy getStrategy() {
            return new MoveRightStrategyImpl();
        }
    };

    public abstract MoveStrategy getStrategy();
}
