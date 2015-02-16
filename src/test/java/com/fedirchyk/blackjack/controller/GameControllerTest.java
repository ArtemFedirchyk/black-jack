package com.fedirchyk.blackjack.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fedirchyk.blackjack.exceptions.constatnts.ExceptionConstants;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.GameStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@WebAppConfiguration
public class GameControllerTest {

    @Autowired
    private GameController gameController;

    @Autowired
    private AccountController accountController;

    private MockMvc mockMvcGameController;

    private MockMvc mockMvcAccountController;

    @Before
    public void init() {
        if (mockMvcAccountController != null || mockMvcGameController != null) {
            Mockito.reset(mockMvcAccountController);
            Mockito.reset(mockMvcGameController);
        }
        MockitoAnnotations.initMocks(this);
        mockMvcGameController = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvcAccountController = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testStartGameIsOk() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/start/50").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testStartGameReturnsJSON() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/start/50").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testStartGameWalletNotFoundExceptionReturnsJSON() throws Exception {
        MockHttpServletRequestBuilder initializePlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initializePlayerResult);

        MockHttpServletRequestBuilder startedGameResult = get("/game/404/start/50").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGameResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testStartGameThrowsWalletNotFoundException() throws Exception {
        MockHttpServletRequestBuilder initializePlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initializePlayerResult);

        MockHttpServletRequestBuilder startedGameResult = get("/game/404/start/50").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGameResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.WALLET_NOT_FOUND));
    }

    @Test
    public void testMakeBetIsOk() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/bet/50").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testMakeBetReturnsJSON() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/bet/50").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testMakeBetThrowsWalletNotFoundExceptionException() throws Exception {
        MockHttpServletRequestBuilder initializePlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initializePlayerResult);

        MockHttpServletRequestBuilder startedGameResult = get("/game/404/bet/50").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGameResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.WALLET_NOT_FOUND));
    }

    @Test
    public void testMakeDealIsOk() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/deal").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testMakeDealReturnsJSON() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/deal").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testMakeDeaThrowsWalletNotFoundException() throws Exception {
        MockHttpServletRequestBuilder initializePlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initializePlayerResult);

        MockHttpServletRequestBuilder startedGameResult = get("/game/404/deal").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGameResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.WALLET_NOT_FOUND));
    }

    @Test
    public void testHitIsOk() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/hit").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testHitReturnsJSON() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/hit").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testHitThrowsWalletNotFoundException() throws Exception {
        MockHttpServletRequestBuilder initializePlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initializePlayerResult);

        MockHttpServletRequestBuilder startedGameResult = get("/game/404/hit").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGameResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.WALLET_NOT_FOUND));
    }

    @Test
    public void testStandIsOk() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/stand").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testStandReturnsJSON() throws Exception {
        MockHttpServletRequestBuilder startedGamPlayerResult = get("/game/1/stand").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGamPlayerResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testStandThrowsWalletNotFoundException() throws Exception {
        MockHttpServletRequestBuilder initializePlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initializePlayerResult);

        MockHttpServletRequestBuilder startedGameResult = get("/game/404/stand").accept(MediaType.ALL);
        ResultActions results = mockMvcGameController.perform(startedGameResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.WALLET_NOT_FOUND));
    }
}
