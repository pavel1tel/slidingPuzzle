package com.testing.slidingpuzzle.service.strategy.impl;

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
class MoveRightStrategyImplTest {

    final Integer BOARD_SIZE = 4;
    private final int EMPTY_TILE_X = 2;
    private final int EMPTY_TILE_Y = 1;
    private final int ELEMENT_TO_MOVE = 4;
    private final int ZERO = 0;
    private final int LAST_COL = BOARD_SIZE - 1;

    @InjectMocks
    private MoveRightStrategyImpl testingInstance;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(testingInstance, "BOARD_SIZE", BOARD_SIZE);
    }

    @Test
    public void shouldMoveRight() {
        GameModel gameModel = spy(GameModel.class);
        gameModel.setBoard(createBoard());

        GameModel result = testingInstance.move(gameModel);

        assertEquals(ELEMENT_TO_MOVE, gameModel.getBoard().get(EMPTY_TILE_Y).get(EMPTY_TILE_X));
        assertEquals(ZERO, gameModel.getBoard().get(EMPTY_TILE_Y).get(EMPTY_TILE_X + 1));
        verifyBoardEqualsExceptTwoTiles(createBoard(), result.getBoard(),
                EMPTY_TILE_X, EMPTY_TILE_Y, EMPTY_TILE_X + 1, EMPTY_TILE_Y);
    }

    @Test
    public void shouldThrowExceptionWhenUnableToMoveLeft() {

        GameModel gameModel = spy(GameModel.class);
        List<List<Integer>> board = createBoard();
        board.get(EMPTY_TILE_Y).set(EMPTY_TILE_X, ELEMENT_TO_MOVE);
        board.get(EMPTY_TILE_Y).set(LAST_COL, ZERO);

        gameModel.setBoard(board);

        assertThrows(RuntimeException.class, () -> testingInstance.move(gameModel));
    }
}