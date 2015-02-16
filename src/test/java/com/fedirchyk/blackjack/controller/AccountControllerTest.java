package com.fedirchyk.blackjack.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.fedirchyk.blackjack.service.implementation.DefaultAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@WebAppConfiguration
public class AccountControllerTest {

    private static final double DEFAULT_BALANCE = 100;

    @Autowired
    private DefaultAccountService defaultAccountService;

    @Autowired
    private AccountController accountController;

    private MockMvc mockMvcAccountController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvcAccountController = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testInitializePlayerWithDefaultBalanceIsOk() throws Exception {
        MockHttpServletRequestBuilder initializatedPlayerResult = get("/initPlayer").accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(initializatedPlayerResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testInitializePlayerWithDefaultBalanceReturnJSON() throws Exception {
        MockHttpServletRequestBuilder initializatedPlayerResult = get("/initPlayer").accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(initializatedPlayerResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testInitializePlayerWithDefaultBalanceCheckBalance() throws Exception {
        MockHttpServletRequestBuilder initializatedPlayerResult = get("/initPlayer").accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(initializatedPlayerResult);

        results.andExpect(jsonPath("$.wallet.balance").value(DEFAULT_BALANCE));
    }

    @Test
    public void testInitializePlayerWithInputedBalanceIsOk() throws Exception {
        MockHttpServletRequestBuilder initializatedPlayerResult = get("/initPlayerBalance/500").accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(initializatedPlayerResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testInitializePlayerWithInputedBalanceReturnJSON() throws Exception {
        MockHttpServletRequestBuilder initializatedPlayerResult = get("/initPlayerBalance/500").accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(initializatedPlayerResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testInitializePlayerWithInputedBalanceCheckBalance() throws Exception {
        String someBalance = "500";
        MockHttpServletRequestBuilder initializatedPlayerResult = get("/initPlayerBalance/" + someBalance).accept(
                MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(initializatedPlayerResult);

        results.andExpect(jsonPath("$.wallet.balance").value(Double.parseDouble(someBalance)));
    }

    @Test
    public void testIncreaseBalanseIsOk() throws Exception {
        MockHttpServletRequestBuilder increaseBalanceResult = get("/addMoney/1/200").accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(increaseBalanceResult);

        results.andExpect(status().isOk());
    }

    @Test
    public void testIncreaseBalanseReturnJSON() throws Exception {
        MockHttpServletRequestBuilder increaseBalanceResult = get("/addMoney/1/200").accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(increaseBalanceResult);

        results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Ignore
    @Test
    public void testIncreaseBalanseReturnCorrectResult() throws Exception {
        String correctWalletID = "1";
        String moneyForIncreasingOfBalance = "300";

        MockHttpServletRequestBuilder initializatedPlayerResult = get("/initPlayer").accept(MediaType.ALL);
        mockMvcAccountController.perform(initializatedPlayerResult);

        MockHttpServletRequestBuilder increaseBalanceResult = get(
                "/addMoney/" + correctWalletID + "/" + moneyForIncreasingOfBalance).accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(increaseBalanceResult);

        results.andExpect(jsonPath("$.wallet.balance").value(
                Double.parseDouble(moneyForIncreasingOfBalance) + DEFAULT_BALANCE));
    }

    @Test
    public void testIncreaseBalanseThrowsWalletNotFoundException() throws Exception {
        get("/initPlayer").accept(MediaType.ALL);

        String wrongWalletID = "88888888";
        String moneyForIncreasingOfBalance = "300";

        MockHttpServletRequestBuilder increaseBalanceResult = get(
                "/addMoney/" + wrongWalletID + "/" + moneyForIncreasingOfBalance).accept(MediaType.ALL);
        ResultActions results = mockMvcAccountController.perform(increaseBalanceResult);

        results.andExpect(jsonPath("$.exception").value(ExceptionConstants.WALLET_NOT_FOUND));
    }
}
