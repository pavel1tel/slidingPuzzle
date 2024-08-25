package com.testing.slidingpuzzle.dao.impl;

import com.testing.slidingpuzzle.dao.GameDao;
import com.testing.slidingpuzzle.model.GameModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GameDaoImpl implements GameDao {

    private final Map<Long, GameModel> games;
    private Long id = 0L;

    @Override
    public Long saveGame(GameModel game) {
        games.put(id, game);
        return id++;
    }

    @Override
    public Optional<GameModel> getGame(Long id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public GameModel updateGame(GameModel game) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeGame(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
