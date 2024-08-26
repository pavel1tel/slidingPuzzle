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
}
