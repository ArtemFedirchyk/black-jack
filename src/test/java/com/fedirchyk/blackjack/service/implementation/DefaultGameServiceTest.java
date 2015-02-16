package com.fedirchyk.blackjack.service.implementation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fedirchyk.blackjack.exceptions.BetAlreadyMadeException;
import com.fedirchyk.blackjack.exceptions.BetNotMadeException;
import com.fedirchyk.blackjack.exceptions.CardsAlreadyDealtException;
import com.fedirchyk.blackjack.exceptions.HitActionNotPosibleException;
import com.fedirchyk.blackjack.exceptions.StandActionNotPosibleException;
import com.fedirchyk.blackjack.exceptions.constatnts.ExceptionConstants;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.service.GameService;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.GameStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@Transactional
public class DefaultGameServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final double DEFAULT_BALANCE = 100;

    private static final int BET_COUNT = 70;

    @Autowired
    private GameService gameService;

    @Autowired
    private AccountService accountService;

    private GameTable gameTable;

    private int walletId;

    @Before
    public void init() {
        gameTable = accountService.initializePlayer(DEFAULT_BALANCE);
        walletId = gameTable.getWallet().getWalletId();
    }

    @Test
    public void testIsBetMadeReturnTrue() {
        gameService.makeBet(walletId, BET_COUNT);
        Assert.assertTrue(gameService.isBetMade(walletId));
    }

    @Test
    public void testIsBetMadeReturnFalse() {
        Assert.assertFalse(gameService.isBetMade(gameTable.getWallet().getWalletId()));
    }

    @Test
    public void testMakeBet() {
        GameTable gameTableWithBet = gameService.makeBet(walletId, BET_COUNT);
        Assert.assertNotNull(gameTableWithBet.getBet());
        Assert.assertEquals(30, gameTableWithBet.getWallet().getBalance(), 0);
    }

    @Test
    public void testCorrectStatusesWhenMadeBet() {
        GameTable gameTableWithBet = gameService.makeBet(walletId, BET_COUNT);
        Assert.assertEquals(GameAction.BET.getAction(), gameTableWithBet.getGameAction());
        Assert.assertEquals(GameStatus.PENDING.getStatus(), gameTableWithBet.getGameStatus());
    }

    @Test
    public void testIsNewGameCreatedAfterFinishingOfPrevious() {
        gameTable.setGameStatus(GameStatus.WIN.getStatus());
        int oldGameId = gameTable.getWallet().getGame().getGameId();
        gameService.makeBet(gameTable.getWallet().getWalletId(), BET_COUNT);
        int newGameId = gameTable.getWallet().getGame().getGameId();
        Assert.assertNotEquals(oldGameId, newGameId);
    }

    @Test
    public void shouldThrownBetAlreadyMadeExceptionThrowsWhenBetAlreadyMade() {
        expectedException.expect(BetAlreadyMadeException.class);
        expectedException.expectMessage(ExceptionConstants.BET_ALREADY_MADE);
        gameService.makeBet(walletId, BET_COUNT);
        gameService.makeBet(walletId, BET_COUNT);
    }

    @Test
    public void testCorrectStatusesWhenDealAction() {
        gameService.makeBet(walletId, BET_COUNT);
        gameService.dealAction(walletId);
        Assert.assertEquals(GameStatus.PENDING.getStatus(), gameTable.getGameStatus());
        Assert.assertEquals(GameAction.DEAL.getAction(), gameTable.getGameAction());
    }

    @Test
    public void shouldThrownBetNotMadeExceptionWhenBetNotMade() {
        expectedException.expect(BetNotMadeException.class);
        expectedException.expectMessage(ExceptionConstants.BET_NOT_MADE);
        gameService.dealAction(gameTable.getWallet().getWalletId());
    }

    @Test
    public void shouldThrownCardsAlreadyDealtExceptionWhenCardAlreadyDealt() {
        expectedException.expect(CardsAlreadyDealtException.class);
        expectedException.expectMessage(ExceptionConstants.CARDS_ALREADY_DEALT);
        gameService.makeBet(walletId, BET_COUNT);
        gameService.dealAction(walletId);
        gameService.dealAction(walletId);
    }

    @Test
    public void testCorrectStatusesWhenHitAction() {
        gameService.makeBet(walletId, BET_COUNT);
        gameService.dealAction(walletId);
        gameService.hitAction(walletId);
        Assert.assertEquals(GameAction.HIT.getAction(), gameTable.getGameAction());
    }

    @Test
    public void shouldThrownHitActionNotPossibleExceptionWhenWrongAction() {
        expectedException.expect(HitActionNotPosibleException.class);
        expectedException.expectMessage(ExceptionConstants.HIT_ACTION_IS_NOT_POSSIBLE_WRONG_ACTION);
        gameTable.setGameStatus(GameStatus.PENDING.getStatus());
        gameTable.setGameAction(GameAction.BET.getAction());
        gameService.hitAction(walletId);
    }

    @Test
    public void shouldThrownHitActionNotPossibleExceptionWhenWrongStatus() {
        expectedException.expect(HitActionNotPosibleException.class);
        expectedException.expectMessage(ExceptionConstants.HIT_ACTION_IS_NOT_POSSIBLE_WRONG_STATUS);
        gameTable.setGameStatus(GameStatus.LOOSE.getStatus());
        gameService.hitAction(walletId);
    }

    @Test
    public void testCorrectStatusWhenStandAction() {
        gameService.makeBet(walletId, BET_COUNT);
        gameService.dealAction(walletId);
        gameTable.setGameAction(GameAction.DEAL.getAction());
        gameTable.setGameStatus(GameStatus.PENDING.getStatus());
        gameService.standAction(walletId);
        Assert.assertEquals(GameAction.STAND.getAction(), gameTable.getGameAction());
    }

    @Test
    public void shouldThrownStandActionNotPossibleExceptionWhenWrongAction() {
        expectedException.expect(StandActionNotPosibleException.class);
        expectedException.expectMessage(ExceptionConstants.STAND_ACTION_IS_NOT_POSSIBLE_WRONG_ACTION);
        gameTable.setGameStatus(GameStatus.PENDING.getStatus());
        gameTable.setGameAction(GameAction.STAND.getAction());
        gameService.standAction(walletId);
    }

    @Test
    public void shouldThrownStandActionNotPossibleExceptionWhenWrongStatus() {
        expectedException.expect(StandActionNotPosibleException.class);
        expectedException.expectMessage(ExceptionConstants.STAND_ACTION_IS_NOT_POSSIBLE_WRONG_STATUS);
        gameTable.setGameStatus(GameStatus.WIN.getStatus());
        gameTable.setGameAction(GameAction.DEAL.getAction());
        gameService.standAction(walletId);
    }
}
