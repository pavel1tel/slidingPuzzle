package com.testing.slidingpuzzle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameModel {
    private List<List<Integer>> board;
    private LocalDateTime startTime;
}
