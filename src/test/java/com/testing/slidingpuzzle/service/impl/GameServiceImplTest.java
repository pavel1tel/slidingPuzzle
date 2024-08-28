package com.testing.slidingpuzzle.service.impl;

import com.testing.slidingpuzzle.dao.GameDao;
import com.testing.slidingpuzzle.dto.GameMoveRequestDto;
import com.testing.slidingpuzzle.enums.MoveDirection;
import com.testing.slidingpuzzle.exceptions.GameAlreadyFinishedException;
import com.testing.slidingpuzzle.exceptions.GameNotFoundException;
import com.testing.slidingpuzzle.mapper.GameMapper;
import com.testing.slidingpuzzle.model.GameModel;
import com.testing.slidingpuzzle.service.strategy.MoveStrategy;
import com.testing.slidingpuzzle.service.strategy.impl.MoveUpStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static com.testing.slidingpuzzle.service.strategy.impl.MoveStrategiesTestingUtils.createSolvedBoard;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    private final long GAME_ID = 1L;

    @Mock
    private GameDao gameDao;
    @Mock
    private Map<MoveDirection, MoveStrategy> moveStrategies;
    @Mock
    private GameMapper gameMapper;
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

        testingInstance.getGame(GAME_ID);

        verify(gameDao).getGame(GAME_ID);
        verify(gameMapper).toDto(gameModel);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenGameIsNotPresent() {
        when(gameDao.getGame(GAME_ID)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> testingInstance.getGame(GAME_ID));
    }

    @Test
    public void shouldMakeMove() {
        GameModel gameModel = mock(GameModel.class);
        GameMoveRequestDto gameMoveRequestDto = mock(GameMoveRequestDto.class);
        MoveUpStrategyImpl mockMoveUpStrategy = mock(MoveUpStrategyImpl.class);
        when(moveStrategies.get(MoveDirection.UP)).thenReturn(mockMoveUpStrategy);
        when(gameMoveRequestDto.direction()).thenReturn(MoveDirection.UP);
        when(gameDao.getGame(GAME_ID)).thenReturn(Optional.of(gameModel));
        when(mockMoveUpStrategy.move(gameModel)).thenReturn(gameModel);

        testingInstance.move(GAME_ID, gameMoveRequestDto);

        assertNotNull(gameModel);
        verify(gameDao).getGame(GAME_ID);
        verify(mockMoveUpStrategy).move(gameModel);
    }

    @Test
    public void shouldMakeMoveAndFinishTheGameWhenBoardIsSolved() {
        GameModel gameModel = mock(GameModel.class);
        GameMoveRequestDto gameMoveRequestDto = mock(GameMoveRequestDto.class);
        MoveUpStrategyImpl mockMoveUpStrategy = mock(MoveUpStrategyImpl.class);
        when(moveStrategies.get(MoveDirection.UP)).thenReturn(mockMoveUpStrategy);
        when(gameMoveRequestDto.direction()).thenReturn(MoveDirection.UP);
        when(gameDao.getGame(GAME_ID)).thenReturn(Optional.of(gameModel));
        when(mockMoveUpStrategy.move(gameModel)).thenReturn(gameModel);
        when(gameModel.getBoard()).thenReturn(createSolvedBoard());

        testingInstance.move(GAME_ID, gameMoveRequestDto);

        assertNotNull(gameModel);
        verify(gameDao).getGame(GAME_ID);
        verify(mockMoveUpStrategy).move(gameModel);
        verify(gameModel).setFinished(true);
        verify(gameModel).setEndTime(any(LocalDateTime.class));
    }

    @Test
    public void shouldThrowExceptionWhenGameIsFinishedAndMoveRequested() {
        GameModel gameModel = mock(GameModel.class);
        GameMoveRequestDto gameMoveRequestDto = mock(GameMoveRequestDto.class);
        when(gameModel.isFinished()).thenReturn(true);
        when(gameDao.getGame(GAME_ID)).thenReturn(Optional.of(gameModel));

        assertThrows(GameAlreadyFinishedException.class, () -> testingInstance.move(GAME_ID, gameMoveRequestDto));
    }
}