package com.testing.slidingpuzzle.service;

import com.testing.slidingpuzzle.dto.CreateGameResponseDto;
import com.testing.slidingpuzzle.dto.GameDto;
import com.testing.slidingpuzzle.dto.GameMoveRequestDto;

import java.util.List;

public interface GameService {
    CreateGameResponseDto createGame();

    GameDto getGame(Long id);

    GameDto move(Long id, GameMoveRequestDto gameMoveRequestDto);

    List<GameDto> getGames();
}
