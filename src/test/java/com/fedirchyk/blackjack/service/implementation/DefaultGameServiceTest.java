package com.fedirchyk.blackjack.service.implementation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.service.GameService;
import com.fedirchyk.blackjack.vo.GameTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@Transactional
public class DefaultGameServiceTest {

    private static final double DEFAULT_BALANCE = 100;

    @Autowired
    private GameService gameService;

    @Autowired
    private AccountService accountService;

    private GameTable gameTable;

    @Before
    public void init() {
        gameTable = accountService.initializePlayer(DEFAULT_BALANCE);
    }

    @Test
    public void testMakeBet() {
        GameTable gameTableWithBet = gameService.makeBet(gameTable.getWallet().getWalletId(), 70);
        Assert.assertEquals(30, gameTableWithBet.getWallet().getBalance(), 0);
    }

    @Test
    public void testIsBetMadeReturnTrue() {
        gameService.makeBet(gameTable.getWallet().getWalletId(), 70);
        Assert.assertTrue(gameService.isBetMade(gameTable.getWallet().getWalletId()));
    }

    @Test
    public void testIsBetMadeReturnFalse() {
        Assert.assertFalse(gameService.isBetMade(gameTable.getWallet().getWalletId()));
    }
}
