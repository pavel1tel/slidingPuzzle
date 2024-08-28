package com.testing.slidingpuzzle.controller;

import com.testing.slidingpuzzle.dto.CreateGameResponseDto;
import com.testing.slidingpuzzle.dto.GameDto;
import com.testing.slidingpuzzle.dto.GameMoveRequestDto;
import com.testing.slidingpuzzle.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;


    @PostMapping
    public CreateGameResponseDto createGame() {
        return gameService.createGame();
    }

    @GetMapping("/{id}")
    public GameDto getGameById(@PathVariable("id") final long id) {
        return gameService.getGame(id);
    }

    @PostMapping("/{id}")
    public GameDto move(@PathVariable("id") final long id,
                        @RequestBody final GameMoveRequestDto gameMoveRequestDto) {
        return gameService.move(id, gameMoveRequestDto);
    }

    @GetMapping()
    public List<GameDto> getGames() {
        return gameService.getGames();
    }
}
