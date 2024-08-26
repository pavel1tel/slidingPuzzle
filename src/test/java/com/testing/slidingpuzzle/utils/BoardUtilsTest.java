package com.testing.slidingpuzzle.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static com.testing.slidingpuzzle.service.strategy.impl.MoveStrategiesTestingUtils.createBoard;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BoardUtilsTest {

    private final int BOARD_SIZE = 4;

    @Test
    public void shouldModifyBoardTo2D() {
        List<Integer> board = createBoard().stream().flatMap(Collection::stream).toList();

        List<List<Integer>> result = BoardUtils.to2Dimensions(board, BOARD_SIZE);

        assert2dListsEquals(createBoard(), result);
    }

    private void assert2dListsEquals(List<List<Integer>> expected, List<List<Integer>> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }


}