package com.testing.slidingpuzzle.service.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MoveStrategiesTestingUtils {

    public static List<List<Integer>> createBoard() {
        List<List<Integer>> board = new ArrayList<>();
        List<Integer> row1 = new ArrayList<>();
        row1.add(13);
        row1.add(7);
        row1.add(5);
        row1.add(14);
        List<Integer> row2 = new ArrayList<>();
        row2.add(2);
        row2.add(3);
        row2.add(0);
        row2.add(4);
        List<Integer> row3 = new ArrayList<>();
        row3.add(1);
        row3.add(10);
        row3.add(12);
        row3.add(8);
        List<Integer> row4 = new ArrayList<>();
        row4.add(15);
        row4.add(11);
        row4.add(6);
        row4.add(9);
        board.add(row1);
        board.add(row2);
        board.add(row3);
        board.add(row4);
        return board;
    }

    public static List<List<Integer>> createSolvedBoard() {
        List<List<Integer>> board = new ArrayList<>();
        List<Integer> row1 = new ArrayList<>();
        row1.add(1);
        row1.add(2);
        row1.add(3);
        row1.add(4);
        List<Integer> row2 = new ArrayList<>();
        row2.add(5);
        row2.add(6);
        row2.add(7);
        row2.add(8);
        List<Integer> row3 = new ArrayList<>();
        row3.add(9);
        row3.add(10);
        row3.add(11);
        row3.add(12);
        List<Integer> row4 = new ArrayList<>();
        row4.add(13);
        row4.add(14);
        row4.add(15);
        row4.add(0);
        board.add(row1);
        board.add(row2);
        board.add(row3);
        board.add(row4);
        return board;
    }

    public static void verifyBoardEqualsExceptTwoTiles(List<List<Integer>> expected, List<List<Integer>> actual,
                                                       int tile1X, int tile1Y, int tile2X, int tile2Y) {
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                if (!(i == tile1Y && j == tile1X) && !(i == tile2Y && j == tile2X)) {
                    assertEquals(expected.get(i).get(j), actual.get(i).get(j));
                }
            }
        }
    }
}
