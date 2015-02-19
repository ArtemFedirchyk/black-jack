package com.fedirchyk.blackjack.service.implementation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.service.LogsService;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.AccountAction;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.OperationType;
import com.fedirchyk.blackjack.vo.enumerations.PlayingSide;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@Transactional
public class DefaultLogsServiceTest {

    private static final double DEFAULT_BALANCE = 100;

    @Autowired
    private LogsService logsService;

    @Autowired
    private AccountService accountService;

    private GameTable gameTable;

    @Before
    public void init() {
        gameTable = accountService.initializePlayer(DEFAULT_BALANCE);
    }

    @Test
    public void testWriteAccountActionLog() {
        Logging savedLogIniitalizationCase = logsService.writeAccountActionLog(gameTable,
                AccountAction.PLAYER_INITIALIZATION.getAccountAction());
        Logging savedLogBalanceRechargeCase = logsService.writeAccountActionLog(gameTable,
                AccountAction.BALANCE_RECHARGE.getAccountAction());

        Assert.assertEquals(savedLogIniitalizationCase.getGame().getGameId(), gameTable.getWallet().getGame()
                .getGameId());
        Assert.assertEquals(savedLogIniitalizationCase.getWallet().getWalletId(), gameTable.getWallet().getWalletId());
        Assert.assertEquals(savedLogIniitalizationCase.getWallet().getWalletId(), gameTable.getWallet().getWalletId());
        Assert.assertEquals(savedLogIniitalizationCase.getOperation(),
                AccountAction.PLAYER_INITIALIZATION.getAccountAction());
        Assert.assertEquals(savedLogIniitalizationCase.getOperationType(),
                OperationType.ACCOUNT_OPERATION.getOperationType());
        Assert.assertEquals(savedLogIniitalizationCase.getPlayingSide(), PlayingSide.PLAYER.getPlaingSide());

        Assert.assertEquals(savedLogBalanceRechargeCase.getOperation(),
                AccountAction.BALANCE_RECHARGE.getAccountAction());
    }

    @Test
    public void testWriteAccountGameActionLogStartGameCase() {
        Logging savedLogStartGamePlayer = logsService.writeGameActionLog(gameTable, GameAction.START_GAME.getAction(),
                PlayingSide.PLAYER.getPlaingSide());

        Assert.assertEquals(savedLogStartGamePlayer.getGame().getGameId(), gameTable.getWallet().getGame().getGameId());
        Assert.assertEquals(savedLogStartGamePlayer.getWallet().getWalletId(), gameTable.getWallet().getWalletId());
        Assert.assertEquals(savedLogStartGamePlayer.getOperationType(), OperationType.GAME_OPERATION.getOperationType());
        Assert.assertEquals(savedLogStartGamePlayer.getPlayingSide(), PlayingSide.PLAYER.getPlaingSide());
        Assert.assertEquals(savedLogStartGamePlayer.getOperation(), GameAction.START_GAME.getAction());
    }
    
    @Test
    public void testWriteAccountGameActionLogBetActionCase(){
        
    }
}
