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
import com.fedirchyk.blackjack.vo.GameTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@Transactional
public class DefaultAccountServiceTest {

    private static final double DEFAULT_BALANCE = 100;

    @Autowired
    private AccountService accountService;

    private GameTable gameTable;

    @Before
    public void init() {
        gameTable = accountService.initializePlayer(DEFAULT_BALANCE);
    }

    @Test
    public void testInitializePlayerReturnedWalletNotNull() {
        Assert.assertNotNull(gameTable.getWallet());
    }

    @Test
    public void testIsWalletExistTrue() {
        boolean isWalletExist = accountService.isWalletExist(gameTable.getWallet().getWalletId());
        Assert.assertTrue(isWalletExist);
    }

    @Test
    public void testIsWalletsBalanceCorrect() {
        Assert.assertEquals(DEFAULT_BALANCE, gameTable.getWallet().getBalance(), 0);
    }

    @Test
    public void testIncreaseBalanseReturnCorrectCount() {
        double increaseCount = 135.25;
        GameTable gameTableWithNewBalance = accountService.increaseWalletsBalance(gameTable.getWallet().getWalletId(),
                increaseCount);
        double newBalance = gameTableWithNewBalance.getWallet().getBalance();
        Assert.assertEquals(235.25, newBalance, 0);
    }

    @Test
    public void testIsPlayerBalanceEnough() {
        int walletId = gameTable.getWallet().getWalletId();
        Assert.assertTrue(accountService.isPlayerBalanceEnough(walletId, 50));
        Assert.assertTrue(accountService.isPlayerBalanceEnough(walletId, 100));
        Assert.assertFalse(accountService.isPlayerBalanceEnough(walletId, 500));
    }
}
