package com.testing.slidingpuzzle.service.strategy.impl;

import com.testing.slidingpuzzle.exceptions.ProhibitedMoveException;
import com.testing.slidingpuzzle.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.testing.slidingpuzzle.service.strategy.impl.MoveStrategiesTestingUtils.createBoard;
import static com.testing.slidingpuzzle.service.strategy.impl.MoveStrategiesTestingUtils.verifyBoardEqualsExceptTwoTiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class MoveUpStrategyImplTest {

    final Integer BOARD_SIZE = 4;
    private final int EMPTY_TILE_X = 2;
    private final int EMPTY_TILE_Y = 1;
    private final int ELEMENT_TO_MOVE = 5;
    private final int ZERO = 0;
    private final int FIRST_ROW = 0;

    @InjectMocks
    private MoveUpStrategyImpl testingInstance;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(testingInstance, "BOARD_SIZE", BOARD_SIZE);
    }

    @Test
    public void shouldMoveUp() {
        GameModel gameModel = spy(GameModel.class);
        gameModel.setBoard(createBoard());

        GameModel result = testingInstance.move(gameModel);

        assertEquals(ELEMENT_TO_MOVE, gameModel.getBoard().get(EMPTY_TILE_Y).get(EMPTY_TILE_X));
        assertEquals(ZERO, gameModel.getBoard().get(EMPTY_TILE_Y - 1).get(EMPTY_TILE_X));
        verifyBoardEqualsExceptTwoTiles(createBoard(), result.getBoard(),
                EMPTY_TILE_X, EMPTY_TILE_Y, EMPTY_TILE_X, EMPTY_TILE_Y - 1);
    }

    @Test
    public void shouldThrowExceptionWhenUnableToMoveUp() {

        GameModel gameModel = spy(GameModel.class);
        List<List<Integer>> board = createBoard();
        board.get(EMPTY_TILE_Y).set(EMPTY_TILE_X, ELEMENT_TO_MOVE);
        board.get(FIRST_ROW).set(EMPTY_TILE_X, ZERO);

        gameModel.setBoard(board);

        assertThrows(ProhibitedMoveException.class, () -> testingInstance.move(gameModel));
    }
}