package com.testing.slidingpuzzle.service.impl;

import com.testing.slidingpuzzle.dao.GameDao;
import com.testing.slidingpuzzle.dto.CreateGameResponseDto;
import com.testing.slidingpuzzle.dto.GameDto;
import com.testing.slidingpuzzle.dto.GameMoveRequestDto;
import com.testing.slidingpuzzle.enums.MoveDirection;
import com.testing.slidingpuzzle.exceptions.GameAlreadyFinishedException;
import com.testing.slidingpuzzle.exceptions.GameNotFoundException;
import com.testing.slidingpuzzle.mapper.GameMapper;
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
    private final GameMapper gameMapper;
    @Value("${board.size}")
    private int BOARD_SIZE;


    @Override
    public CreateGameResponseDto createGame() {
        List<Integer> tiles = new ArrayList<>(IntStream.range(0, BOARD_SIZE * BOARD_SIZE).boxed().toList());
        do {
            Collections.shuffle(tiles);
        } while (!isSolvable(tiles));
        List<List<Integer>> board = new ArrayList<>(BOARD_SIZE * BOARD_SIZE);
        for (int i = 0; i < BOARD_SIZE; i++) {
            board.add(tiles.subList(BOARD_SIZE * i, BOARD_SIZE * i + BOARD_SIZE));
        }
        return new CreateGameResponseDto(gameDao.saveGame(new GameModel(board, LocalDateTime.now())));
    }

    @Override
    public GameDto getGame(Long id) {
        return gameMapper.toDto(gameDao.getGame(id).orElseThrow(() -> new GameNotFoundException("Game not found")));
    }

    @Override
    public GameDto move(Long id, GameMoveRequestDto gameMoveRequestDto) {
        GameModel gameModel = gameDao.getGame(id).orElseThrow(() -> new GameNotFoundException("Game not found"));
        if (gameModel.isFinished()) {
            throw new GameAlreadyFinishedException("Game is already finished");
        }
        GameModel updatedModel = moveStrategies.get(gameMoveRequestDto.direction()).move(gameModel);
        if (isGameFinished(updatedModel.getBoard())) {
            updatedModel.setEndTime(LocalDateTime.now());
            updatedModel.setFinished(true);
        }
        return gameMapper.toDto(updatedModel);
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

    private boolean isGameFinished(List<List<Integer>> board) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                if (i == board.size() - 1 && j == board.size() - 1) {
                    continue;
                }
                if (board.get(i).get(j) != ((i * board.size()) + j) + 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
