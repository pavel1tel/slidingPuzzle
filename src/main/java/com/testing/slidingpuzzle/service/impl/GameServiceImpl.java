package com.testing.slidingpuzzle.service.impl;

import com.testing.slidingpuzzle.dao.GameDao;
import com.testing.slidingpuzzle.dto.GameMoveRequestDto;
import com.testing.slidingpuzzle.enums.MoveDirection;
import com.testing.slidingpuzzle.model.GameModel;
import com.testing.slidingpuzzle.service.GameService;
import com.testing.slidingpuzzle.service.strategy.MoveStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;
    private final Map<MoveDirection, MoveStrategy> moveStrategies;
    @Value("${board.size}")
    private int BOARD_SIZE;


    @Override
    public Long createGame() {
        List<Integer> tiles = new ArrayList<>(IntStream.range(0, BOARD_SIZE * BOARD_SIZE).boxed().toList());
        do {
            Collections.shuffle(tiles);
        } while (!isSolvable(tiles));
        List<List<Integer>> board = new ArrayList<>(BOARD_SIZE * BOARD_SIZE);
        for (int i = 0; i < BOARD_SIZE; i++) {
            board.add(tiles.subList(BOARD_SIZE * i, BOARD_SIZE * i + BOARD_SIZE));
        }
        return gameDao.saveGame(new GameModel(board, LocalDateTime.now()));
    }

    @Override
    public GameModel getGame(Long id) {
        return gameDao.getGame(id).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    @Override
    public GameModel move(Long id, GameMoveRequestDto gameMoveRequestDto) {
        GameModel gameModel = getGame(id);
        return moveStrategies.get(gameMoveRequestDto.direction()).move(gameModel);
    }

    private boolean isSolvable(List<Integer> tiles) {
        int inversions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i) > tiles.get(j) && tiles.get(j) != 0) {
                    inversions++;
                }
            }
        }
        int blankRow = BOARD_SIZE - (tiles.indexOf(0) / BOARD_SIZE);
        if (BOARD_SIZE % 2 != 0) {
            return inversions % 2 == 0;
        }
        return (blankRow % 2 == 0) == (inversions % 2 != 0);
    }
}
