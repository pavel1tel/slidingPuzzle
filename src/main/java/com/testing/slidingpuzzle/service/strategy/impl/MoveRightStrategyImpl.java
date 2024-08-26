package com.testing.slidingpuzzle.service.strategy.impl;

import com.testing.slidingpuzzle.exceptions.ProhibitedMoveException;
import com.testing.slidingpuzzle.model.GameModel;
import com.testing.slidingpuzzle.service.strategy.MoveStrategy;
import com.testing.slidingpuzzle.utils.BoardUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class MoveRightStrategyImpl implements MoveStrategy {

    @Value("${board.size}")
    private int BOARD_SIZE;

    @Override
    public GameModel move(final GameModel gameModel) {
        List<Integer> boardFlat = new ArrayList<>(gameModel.getBoard().stream().flatMap(Collection::stream).toList());
        int emptyTileIndex = boardFlat.indexOf(0);
        int blankCol = emptyTileIndex % BOARD_SIZE;

        if (blankCol == BOARD_SIZE - 1) {
            throw new ProhibitedMoveException("Unable to move empty tile right");
        }

        int newEmptyTile = emptyTileIndex + 1;
        Collections.swap(boardFlat, emptyTileIndex, newEmptyTile);

        gameModel.setBoard(BoardUtils.to2Dimensions(boardFlat, BOARD_SIZE));
        return gameModel;
    }
}
