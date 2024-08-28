package com.testing.slidingpuzzle.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class BoardUtils {

    public static List<List<Integer>> to2Dimensions(List<Integer> board, int boardSize) {
        return IntStream.range(0, board.size() / boardSize)
                .mapToObj(i -> board.subList(i * boardSize, i * boardSize + boardSize))
                .map(ArrayList::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean isSolvable(final List<Integer> tiles, int BOARD_SIZE) {
        int inversions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i) > tiles.get(j) && tiles.get(j) != 0) {
                    inversions++;
                }
            }
        }
        int blankRow = BOARD_SIZE - (tiles.indexOf(0) / BOARD_SIZE);
        if (BOARD_SIZE % 2 != 0) {
            return inversions % 2 == 0;
        }
        return (blankRow % 2 == 0) == (inversions % 2 != 0);
    }

    public static boolean isGameFinished(final List<List<Integer>> board) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                if (i == board.size() - 1 && j == board.size() - 1) {
                    continue;
                }
                if (board.get(i).get(j) != ((i * board.size()) + j) + 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
