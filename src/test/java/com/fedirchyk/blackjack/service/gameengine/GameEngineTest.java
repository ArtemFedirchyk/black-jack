package com.fedirchyk.blackjack.service.gameengine;

import java.util.Stack;

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
import com.fedirchyk.blackjack.vo.Card;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.GameStatus;
import com.fedirchyk.blackjack.vo.enumerations.Rank;
import com.fedirchyk.blackjack.vo.enumerations.Suit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@Transactional
public class GameEngineTest {

    private static final double DEFAULT_BALANCE = 100;

    private static final int BET_COUNT = 50;

    private static final int BLACK_JACK_COMBINATION = 21;

    private static final int DEALER_MAX_INITIAL_SCORERS_COUNT = 17;

    private static final int INITIAL_STATE = 0;

    @Autowired
    private GameService gameService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GameEngine gameEngine;

    private GameTable gameTable;

    @Before
    public void init() {
        gameTable = accountService.initializePlayer(DEFAULT_BALANCE);
    }

    @Test
    public void testDealCards() {
        Stack<Card> cardDeck = gameTable.getCardDeck();

        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        Stack<Card> playerCards = gameTable.getPlayerCards();
        Stack<Card> dealerCards = gameTable.getDealerCards();

        Card playerCardFirst = playerCards.get(0);
        Card playerCardSecond = playerCards.get(1);
        Card dealerCardFirst = dealerCards.get(0);
        Card dealerHiddenCard = gameTable.getDealerHiddenCard();

        Assert.assertNotNull(playerCards);
        Assert.assertNotNull(dealerCards);

        Assert.assertEquals(Rank.ACE, playerCardFirst.getRank());
        Assert.assertEquals(Suit.SPADES, playerCardFirst.getSuit());
        Assert.assertEquals(Rank.ACE, playerCardSecond.getRank());
        Assert.assertEquals(Suit.HEARTS, playerCardSecond.getSuit());
        Assert.assertEquals(Rank.ACE, dealerCardFirst.getRank());
        Assert.assertEquals(Suit.DIAMONDS, dealerCardFirst.getSuit());
        Assert.assertEquals(Rank.ACE, dealerHiddenCard.getRank());
        Assert.assertEquals(Suit.CLUBS, dealerHiddenCard.getSuit());
    }

    @Test
    public void testCountPlayerScores() {
        Stack<Card> cardDeck = gameTable.getCardDeck();

        int playerScoresBeoreCounting = gameTable.getPlayerScores();
        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        gameEngine.countPlayerScores(gameTable);
        int playerScoresAfterCounting = gameTable.getPlayerScores();

        Assert.assertNotEquals(playerScoresBeoreCounting, playerScoresAfterCounting);
        Assert.assertEquals(playerScoresAfterCounting, 12);
    }

    @Test
    public void testCountDealerScores() {
        Stack<Card> cardDeck = gameTable.getCardDeck();

        int dealerScoresBeoreCounting = gameTable.getDealerScores();
        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        gameTable.getDealerCards().add(gameTable.getDealerHiddenCard());
        gameEngine.countDealerScores(gameTable);
        int dealerScoresAfterCounting = gameTable.getDealerScores();

        Assert.assertNotEquals(dealerScoresBeoreCounting, dealerScoresAfterCounting);
        Assert.assertEquals(dealerScoresAfterCounting, 12);
    }

    @Test
    public void testInvestigateGameWhenBlackJackPlayerCase() {
        gameTable.setGameAction(GameAction.START_GAME.getAction());
        gameTable.getWallet().setBalance(DEFAULT_BALANCE);
        gameTable.setBet(BET_COUNT);
        gameTable.setPlayerScores(BLACK_JACK_COMBINATION);

        double balanceBeforeBJ = gameTable.getWallet().getBalance();
        double balanceAfterBJ = balanceBeforeBJ + BET_COUNT + BET_COUNT * 1.5;
        Stack<Card> cardDeck = gameTable.getCardDeck();
        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        gameEngine.investigateGame(gameTable, gameTable.getGameAction());

        Assert.assertEquals(GameStatus.WIN.getStatus(), gameTable.getGameStatus());
        Assert.assertEquals(balanceAfterBJ, gameTable.getWallet().getBalance(), 0);
        Assert.assertEquals(gameTable.getBet(), INITIAL_STATE);
        Assert.assertNotEquals(gameTable.getBet(), BET_COUNT);
    }

    @Test
    public void testInvestigateGameWhenBlackJackPlayerAndDealerCase() {
        gameTable.setGameAction(GameAction.START_GAME.getAction());
        gameTable.getWallet().setBalance(DEFAULT_BALANCE);
        gameTable.setBet(BET_COUNT);
        gameTable.setPlayerScores(BLACK_JACK_COMBINATION);

        double balanceBeforeBJ = gameTable.getWallet().getBalance();
        double balanceAfterBJ = balanceBeforeBJ + BET_COUNT;
        Stack<Card> cardDeck = gameTable.getCardDeck();
        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        gameTable.setDealerHiddenCard(new Card(Rank.JACK, Suit.DIAMONDS));

        gameEngine.investigateGame(gameTable, gameTable.getGameAction());

        Assert.assertEquals(gameTable.getGameStatus(), GameStatus.DRAW.getStatus());
        Assert.assertEquals(balanceAfterBJ, gameTable.getWallet().getBalance(), 0);
        Assert.assertEquals(gameTable.getBet(), INITIAL_STATE);
        Assert.assertNotEquals(gameTable.getBet(), BET_COUNT);
    }

    @Test
    public void testHitActionWhenPlayersScoresMoreThanBJ() {
        gameTable.setGameAction(GameAction.HIT.getAction());
        gameTable.setBet(BET_COUNT);
        Stack<Card> cardDeck = gameTable.getCardDeck();

        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        cardDeck.add(new Card(Rank.JACK, Suit.DIAMONDS));
        gameEngine.investigateGame(gameTable, gameTable.getGameAction());

        Assert.assertEquals(gameTable.getGameStatus(), GameStatus.LOOSE.getStatus());
        Assert.assertEquals(gameTable.getBet(), INITIAL_STATE);
        Assert.assertNotEquals(gameTable.getBet(), BET_COUNT);
    }

    @Test
    public void testStandActionWhenPlayerWins() {
        gameTable.setGameAction(GameAction.STAND.getAction());
        gameTable.getWallet().setBalance(DEFAULT_BALANCE);
        gameTable.setBet(BET_COUNT);
        gameTable.setPlayerScores(BLACK_JACK_COMBINATION);
        Stack<Card> cardDeck = gameTable.getCardDeck();

        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        cardDeck.add(new Card(Rank.JACK, Suit.DIAMONDS));
        gameEngine.investigateGame(gameTable, gameTable.getGameAction());

        Assert.assertEquals(gameTable.getGameStatus(), GameStatus.WIN.getStatus());
        Assert.assertEquals(gameTable.getWallet().getBalance(), DEFAULT_BALANCE + BET_COUNT * 2, 0);
        Assert.assertEquals(gameTable.getBet(), INITIAL_STATE);
        Assert.assertNotEquals(gameTable.getBet(), BET_COUNT);
    }

    @Test
    public void testStandActionWhenPlayerLoose() {
        gameTable.setGameAction(GameAction.STAND.getAction());
        gameTable.setBet(BET_COUNT);
        Stack<Card> cardDeck = gameTable.getCardDeck();

        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        gameEngine.countPlayerScores(gameTable);
        gameTable.setDealerHiddenCard(new Card(Rank.EIGHT, Suit.DIAMONDS));
        gameTable.getDealerCards().add(gameTable.getDealerHiddenCard());
        cardDeck.add(new Card(Rank.TWO, Suit.DIAMONDS));
        gameEngine.investigateGame(gameTable, gameTable.getGameAction());

        Assert.assertEquals(gameTable.getGameStatus(), GameStatus.LOOSE.getStatus());
        Assert.assertEquals(gameTable.getBet(), INITIAL_STATE);
        Assert.assertNotEquals(gameTable.getBet(), BET_COUNT);
    }

    @Test
    public void testStandActionPlayerDraw() {
        gameTable.setGameAction(GameAction.STAND.getAction());
        gameTable.getWallet().setBalance(DEFAULT_BALANCE);
        gameTable.setBet(BET_COUNT);
        Stack<Card> cardDeck = gameTable.getCardDeck();

        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        gameTable.setPlayerScores(DEALER_MAX_INITIAL_SCORERS_COUNT);
        gameTable.setDealerHiddenCard(new Card(Rank.SIX, Suit.DIAMONDS));
        gameEngine.investigateGame(gameTable, gameTable.getGameAction());

        Assert.assertEquals(gameTable.getGameStatus(), GameStatus.DRAW.getStatus());
        Assert.assertEquals(gameTable.getWallet().getBalance(), DEFAULT_BALANCE + BET_COUNT, 0);
        Assert.assertEquals(gameTable.getBet(), INITIAL_STATE);
        Assert.assertNotEquals(gameTable.getBet(), BET_COUNT);
    }

    @Test
    public void testInvestigateGameWhenBlackJackDealerCase() {
        gameTable.setGameAction(GameAction.STAND.getAction());
        gameTable.setBet(BET_COUNT);
        Stack<Card> cardDeck = gameTable.getCardDeck();

        initializationTestCardDeck(cardDeck);
        gameEngine.dealCards(gameTable);
        gameTable.setPlayerScores(DEALER_MAX_INITIAL_SCORERS_COUNT);
        gameTable.setDealerHiddenCard(new Card(Rank.JACK, Suit.DIAMONDS));
        gameEngine.investigateGame(gameTable, gameTable.getGameAction());

        Assert.assertEquals(gameTable.getGameStatus(), GameStatus.LOOSE.getStatus());
        Assert.assertEquals(gameTable.getBet(), INITIAL_STATE);
        Assert.assertNotEquals(gameTable.getBet(), BET_COUNT);
    }

    private void initializationTestCardDeck(Stack<Card> cardDeck) {
        cardDeck.add(new Card(Rank.ACE, Suit.CLUBS));
        cardDeck.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cardDeck.add(new Card(Rank.ACE, Suit.HEARTS));
        cardDeck.add(new Card(Rank.ACE, Suit.SPADES));
    }
}
