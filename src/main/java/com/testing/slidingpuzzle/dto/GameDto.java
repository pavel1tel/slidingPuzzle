package com.testing.slidingpuzzle.dto;

import java.time.LocalDateTime;
import java.util.List;

public record GameDto(List<List<Integer>> board, LocalDateTime startTime, LocalDateTime endTime) {
}
