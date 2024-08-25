package com.testing.slidingpuzzle.service.impl;

import com.testing.slidingpuzzle.dao.GameDao;
import com.testing.slidingpuzzle.dto.GameMoveRequestDto;
import com.testing.slidingpuzzle.enums.MoveDirection;
import com.testing.slidingpuzzle.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    private final long GAME_ID = 1L;

    @Mock
    private GameDao gameDao;
    @InjectMocks
    private GameServiceImpl testingInstance;

    @BeforeEach
    void setUp() {
        final Integer BOARD_SIZE = 4;
        ReflectionTestUtils.setField(testingInstance, "BOARD_SIZE", BOARD_SIZE);
    }

    @Test
    public void shouldCreateNewGame() {
        testingInstance.createGame();

        verify(gameDao).saveGame(any(GameModel.class));
    }

    @Test
    public void shouldGetGameWhenGameIsPresent() {
        GameModel gameModel = mock(GameModel.class);
        when(gameDao.getGame(GAME_ID)).thenReturn(Optional.ofNullable(gameModel));

        GameModel result = testingInstance.getGame(GAME_ID);

        verify(gameDao).getGame(GAME_ID);
        assertEquals(gameModel, result);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenGameIsNotPresent() {
        when(gameDao.getGame(GAME_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> testingInstance.getGame(GAME_ID));
    }

    @Test
    public void shouldMakeMove() {
        GameModel gameModel = mock(GameModel.class);
        GameMoveRequestDto gameMoveRequestDto = mock(GameMoveRequestDto.class);
        when(gameMoveRequestDto.direction()).thenReturn(MoveDirection.UP);
        when(gameDao.getGame(GAME_ID)).thenReturn(Optional.ofNullable(gameModel));

        testingInstance.move(GAME_ID, gameMoveRequestDto);
        verify(gameDao).getGame(GAME_ID);
        verify(gameMoveRequestDto).direction();
    }
}