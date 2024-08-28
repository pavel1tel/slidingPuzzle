package com.testing.slidingpuzzle.controller;

import com.testing.slidingpuzzle.dto.GameMoveRequestDto;
import com.testing.slidingpuzzle.enums.MoveDirection;
import com.testing.slidingpuzzle.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private final long GAME_ID = 1L;

    @Mock
    private GameService gameService;
    @InjectMocks
    private GameController testingInstance;

    @Test
    public void shouldCreateGame() {
        testingInstance.createGame();

        verify(gameService).createGame();
    }

    @Test
    public void shouldGetGame() {
        testingInstance.getGameById(GAME_ID);

        verify(gameService).getGame(GAME_ID);
    }

    @Test
    public void shouldMove() {
        GameMoveRequestDto gameMoveRequestDto = new GameMoveRequestDto(MoveDirection.DOWN);
        testingInstance.move(GAME_ID, gameMoveRequestDto);

        verify(gameService).move(GAME_ID, gameMoveRequestDto);
    }
}