package com.testing.slidingpuzzle.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GameModel {
    @NonNull
    private List<List<Integer>> board;
    @NonNull
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isFinished;
}
