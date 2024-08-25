package com.testing.slidingpuzzle.service;

import com.testing.slidingpuzzle.dto.GameMoveRequestDto;
import com.testing.slidingpuzzle.model.GameModel;

public interface GameService {
    Long createGame();

    GameModel getGame(Long id);

    GameModel move(Long id,GameMoveRequestDto gameMoveRequestDto);

}
