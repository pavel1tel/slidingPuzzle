package com.testing.slidingpuzzle.controller;

import com.testing.slidingpuzzle.dao.GameDao;
import com.testing.slidingpuzzle.model.GameModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.testing.slidingpuzzle.service.strategy.impl.MoveStrategiesTestingUtils.createBoard;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class GameControllerIntegrationTest {

    @SpyBean
    private GameDao gameDao;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateGame() throws Exception {
        mockMvc.perform(post("/api/game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("0"))
        ;
    }

    @Test
    public void shouldCreateGameTwiceWithSameIdWhenNewSession() throws Exception {
        mockMvc.perform(post("/api/game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("0"))
                .andReturn();

        mockMvc.perform(post("/api/game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("0"));
    }

    @Test
    public void shouldCreateGameTwiceWithNewIdWhenSameSession() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("0"))
                .andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();

        mockMvc.perform(post("/api/game").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("1"));
    }

    @Test
    public void shouldGetGameById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("0"))
                .andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();

        mockMvc.perform(get("/api/game/0").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.board").isArray());
    }

    @Test
    public void shouldThrowExceptionWhenGameIsNotFound() throws Exception {
        mockMvc.perform(get("/api/game/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void shouldMakeMove() throws Exception {
        GameModel gameModel = new GameModel(createBoard(), LocalDateTime.now());
        when(gameDao.getGame(0L)).thenReturn(Optional.of(gameModel));

        mockMvc.perform(post("/api/game/0")
                        .content("{\"direction\" : \"UP\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.board").isArray());
    }

    @Test
    public void shouldThrowExceptionWhenMoveIsProhibited() throws Exception {
        GameModel gameModel = new GameModel(createBoard(), LocalDateTime.now());
        when(gameDao.getGame(0L)).thenReturn(Optional.of(gameModel));

        MvcResult mvcResult = mockMvc.perform(post("/api/game/0")
                        .content("{\"direction\" : \"UP\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.board").isArray())
                .andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();

        mockMvc.perform(post("/api/game/0")
                        .content("{\"direction\" : \"UP\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

}