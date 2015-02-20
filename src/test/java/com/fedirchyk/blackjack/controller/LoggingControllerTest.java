package com.fedirchyk.blackjack.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@WebAppConfiguration
public class LoggingControllerTest {

    @Autowired
    private LoggingController loggingController;

    @Autowired
    private AccountController accountController;

    private MockMvc mockMvcLoggingController;

    private MockMvc mockMvcAccountController;

    @Before
    public void init() throws Exception {
        if (mockMvcLoggingController != null || mockMvcAccountController != null) {
            Mockito.reset(mockMvcLoggingController);
        }
        MockitoAnnotations.initMocks(this);
        mockMvcLoggingController = MockMvcBuilders.standaloneSetup(loggingController).build();
        mockMvcAccountController = MockMvcBuilders.standaloneSetup(accountController).build();

        MockHttpServletRequestBuilder initPlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initPlayerResult);
    }

    @Test
    public void testGetNeededGameLogsIsOk() throws Exception {
        MockHttpServletRequestBuilder gameLogsResult = get("/gameLogs/1").accept(MediaType.ALL);
        ResultActions results = mockMvcLoggingController.perform(gameLogsResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testGetNeededGameLogsthrowsGameNotFoundException() throws Exception {
        MockHttpServletRequestBuilder gameLogsResult = get("/gameLogs/555").accept(MediaType.ALL);
        ResultActions results = mockMvcLoggingController.perform(gameLogsResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.GAME_NOT_FOUND));
    }

    @Test
    public void testgetNeededAccountLogsIsOk() throws Exception {
        MockHttpServletRequestBuilder gameLogsResult = get("/playerLogs/1").accept(MediaType.ALL);
        ResultActions results = mockMvcLoggingController.perform(gameLogsResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testGetNeededGameLogsthrowsWalletNotFoundException() throws Exception {
        MockHttpServletRequestBuilder gameLogsResult = get("/playerLogs/555").accept(MediaType.ALL);
        ResultActions results = mockMvcLoggingController.perform(gameLogsResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.WALLET_NOT_FOUND));
    }
}
