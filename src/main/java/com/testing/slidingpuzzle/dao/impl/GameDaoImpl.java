package com.testing.slidingpuzzle.dao.impl;

import com.testing.slidingpuzzle.dao.GameDao;
import com.testing.slidingpuzzle.model.GameModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GameDaoImpl implements GameDao {

    private final Map<Long, GameModel> games;

    @Override
    public Long saveGame(GameModel game) {
        games.put((long) games.keySet().size(), game);
        return (long) (games.keySet().size() - 1);
    }

    @Override
    public Optional<GameModel> getGame(Long id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public List<GameModel> getGames() {
        return games.values().stream().toList();
    }
}
