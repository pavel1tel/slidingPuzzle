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

import static com.testing.slidingpuzzle.utils.BoardUtils.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final String GAME_NOT_FOUND_EXCEPTION = "Game not found";
    private final String GAME_ALREADY_FINISHED_EXCEPTION = "Game is already finished";

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
        } while (!isSolvable(tiles, BOARD_SIZE));
        List<List<Integer>> board = to2Dimensions(tiles, BOARD_SIZE);
        return new CreateGameResponseDto(gameDao.saveGame(new GameModel(board, LocalDateTime.now())));
    }

    @Override
    public GameDto getGame(final Long id) {
        return gameMapper.toDto(gameDao.getGame(id).orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND_EXCEPTION)));
    }

    @Override
    public List<GameDto> getGames() {
        return gameMapper.toDtoList(gameDao.getGames());
    }

    @Override
    public GameDto move(final Long id, final GameMoveRequestDto gameMoveRequestDto) {
        GameModel gameModel = gameDao.getGame(id).orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND_EXCEPTION));
        if (gameModel.isFinished()) {
            throw new GameAlreadyFinishedException(GAME_ALREADY_FINISHED_EXCEPTION);
        }
        GameModel updatedModel = moveStrategies.get(gameMoveRequestDto.direction()).move(gameModel);
        if (isGameFinished(updatedModel.getBoard())) {
            updatedModel.setEndTime(LocalDateTime.now());
            updatedModel.setFinished(true);
        }
        return gameMapper.toDto(updatedModel);
    }
}
