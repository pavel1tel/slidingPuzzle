package com.testing.slidingpuzzle.dao;

import com.testing.slidingpuzzle.model.GameModel;

import java.util.Optional;

public interface GameDao {
    Long saveGame(GameModel game);

    Optional<GameModel> getGame(Long id);

    GameModel updateGame(GameModel game);

    void removeGame(int id);
}
