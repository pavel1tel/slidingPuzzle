package com.testing.slidingpuzzle.dao.impl;

import com.testing.slidingpuzzle.model.GameModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameDaoImplTest {

    private final long GAME_ID = 1L;

    @Mock
    private Map<Long, GameModel> games;
    @InjectMocks
    private GameDaoImpl testingInstance;

    @Test
    public void shouldSaveGame() {
        GameModel gameModel = mock(GameModel.class);

        Long result = testingInstance.saveGame(gameModel);

        verify(games).put(result, gameModel);
    }

    @Test
    public void shouldIncrementCounterWhenGameIsCreated() {
        GameModel gameModel = mock(GameModel.class);

        Long result = testingInstance.saveGame(gameModel);
        Long result2 = testingInstance.saveGame(gameModel);

        assertEquals(result + 1, result2);
    }

    @Test
    public void shouldGetGameWhenGameIsFound() {
        GameModel gameModel = mock(GameModel.class);
        when(games.get(GAME_ID)).thenReturn(gameModel);

        Optional<GameModel> result = testingInstance.getGame(GAME_ID);

        verify(games).get(GAME_ID);
        assertTrue(result.isPresent());
        assertEquals(gameModel, result.get());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenGameIsNotFound() {
        when(games.get(GAME_ID)).thenReturn(null);

        Optional<GameModel> result = testingInstance.getGame(GAME_ID);

        verify(games).get(GAME_ID);
        assertTrue(result.isEmpty());
    }
}