package com.fedirchyk.blackjack.service.gameengine;

import java.util.Collections;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fedirchyk.blackjack.service.LogsService;
import com.fedirchyk.blackjack.vo.Card;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.GameStatus;
import com.fedirchyk.blackjack.vo.enumerations.PlayingSide;
import com.fedirchyk.blackjack.vo.enumerations.Rank;
import com.fedirchyk.blackjack.vo.enumerations.Suit;

/**
 * Contains all low level logic for performing of Game process actions, such as shuffling cards, dealing cards etc.
 * 
 * @author dell
 * 
 */
@Component
public class GameEngine {

    private static Logger logger = Logger.getLogger(GameEngine.class);

    private static final int BLACK_JACK_COMBINATION = 21;

    private static final int DEALER_MAX_INITIAL_SCORERS_COUNT = 17;

    private static final int MAX_HIT_CARDS_SCORES_COUNT = 10;

    private static final int INITIAL_STATE = 0;

    @Autowired
    private LogsService logsService;

    /**
     * Performs initialization of Deck of Cards for new game and hangs out all Cards inside Deck in random order
     * 
     * @param cardDeck
     *            - stack which contains Cards in random order
     */
    public void hangOutCardsDeck(Stack<Card> cardDeck) {
        logger.info("Started process of hanging out Deck of Cards for new game");

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cardDeck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cardDeck);
    }

    /**
     * Performs the process of dealing Cards for Player and Dealer
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    public void dealCards(GameTable gameTable) {
        logger.info("Started process of dealing Cards");

        gameTable.getPlayerCards().add(gameTable.getCardDeck().pop());
        gameTable.getPlayerCards().add(gameTable.getCardDeck().pop());
        gameTable.getDealerCards().add(gameTable.getCardDeck().pop());
        gameTable.setDealerHiddenCard(gameTable.getCardDeck().pop());
    }

    /**
     * Performs counting of Player's scores during Game process
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    public void countPlayerScores(GameTable gameTable) {
        logger.info("Started process of counting scores for Player");

        int playerScores = 0;
        Stack<Card> playerCards = gameTable.getPlayerCards();
        for (Card playerCard : playerCards) {
            playerScores += playerCard.getRank().getCardValue();
        }
        if (playerScores > BLACK_JACK_COMBINATION && isCardsContainAce(playerCards)) {
            logger.info("Situation when Ace gives 1 score instead 10 in case with Player");
            playerScores -= MAX_HIT_CARDS_SCORES_COUNT;
        }
        gameTable.setPlayerScores(playerScores);
        logger.info("Return scores for Player with count - [" + playerScores + "]");
    }

    /**
     * Performs counting of Dealer's scores during Game process
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    public void countDealerScores(GameTable gameTable) {
        logger.info("Started process of counting scores for Dealer");

        int dealerScores = 0;
        Stack<Card> dealerCards = gameTable.getDealerCards();
        for (Card dealerCard : dealerCards) {
            dealerScores += dealerCard.getRank().getCardValue();
        }
        if (dealerScores > BLACK_JACK_COMBINATION && isCardsContainAce(dealerCards)) {
            logger.info("Situation when Ace gives 1 score instead 10 in case with Dealer");
            dealerScores -= MAX_HIT_CARDS_SCORES_COUNT;
        }
        gameTable.setDealerScores(dealerScores);
        logger.info("Return scores for Dealer with count - [" + dealerScores + "]");
    }

    /**
     * Investigate Game process and performs all needed actions, according to kind of <b>Game action</b>
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     * @param gameAction
     *            - value of kind of Game action
     */
    public void investigateGame(GameTable gameTable, String gameAction) {
        if (gameAction.equals(GameAction.START_GAME.getAction())
                && gameTable.getPlayerScores() == BLACK_JACK_COMBINATION) {
            investigatePlayerBlackJackCase(gameTable);
        }

        if (gameAction.equals(GameAction.HIT.getAction())) {
            hitAction(gameTable);
        }

        if (gameAction.equals(GameAction.STAND.getAction())) {
            standAction(gameTable);
        }
    }

    /**
     * Destroy Game after finishing of this Game, it means this method set initial state for all Game's attributes. And
     * after that new Game process will be possible.
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    public void destroyGame(GameTable gameTable) {
        logger.info("Started process of destroing Game with ID - " + gameTable.getWallet().getGame().getGameId());

        gameTable.setBet(INITIAL_STATE);
        gameTable.setPlayerScores(INITIAL_STATE);
        gameTable.setDealerScores(INITIAL_STATE);
        gameTable.setCardDeck(new Stack<Card>());
        gameTable.setDealerCards(new Stack<Card>());
        gameTable.setPlayerCards(new Stack<Card>());
        gameTable.setGameStatus(null);
        gameTable.setGameAction(null);
    }

    /**
     * Performs all low-level logic associated with HIT action
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    private void hitAction(GameTable gameTable) {
        logger.info("Performing of HIT action (low-level)");

        gameTable.getPlayerCards().add(gameTable.getCardDeck().pop());
        countPlayerScores(gameTable);
        if (gameTable.getPlayerScores() > BLACK_JACK_COMBINATION) {
            logger.info("Case when Player loosed game");
            gameTable.setGameStatus(GameStatus.LOOSE.getStatus());
            gameTable.setBet(INITIAL_STATE);
            gameTable.getDealerCards().push(gameTable.getDealerHiddenCard());
            countDealerScores(gameTable);
        }
    }

    /**
     * Performs all low-level logic associated with STAND action
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    private void standAction(GameTable gameTable) {
        logger.info("Performing of STAND action (low-level)");

        dealerGame(gameTable);

        logsService.writeGameActionLog(gameTable, GameAction.STAND.getAction(), PlayingSide.DEALER.getPlaingSide());

        int dealerScores = gameTable.getDealerScores();
        int playerScores = gameTable.getPlayerScores();

        if (playerScores > dealerScores || dealerScores > BLACK_JACK_COMBINATION) {
            gameTable.setGameStatus(GameStatus.WIN.getStatus());
            double walletBalanceWinCase = gameTable.getWallet().getBalance() + (gameTable.getBet() * 2);
            gameTable.getWallet().setBalance(walletBalanceWinCase);
            gameTable.setBet(INITIAL_STATE);
        } else if (playerScores < dealerScores) {
            gameTable.setGameStatus(GameStatus.LOOSE.getStatus());
            gameTable.setBet(INITIAL_STATE);
        } else if (playerScores == dealerScores) {
            gameTable.setGameStatus(GameStatus.DRAW.getStatus());
            double walletBalanceDrawCase = gameTable.getWallet().getBalance() + gameTable.getBet();
            gameTable.getWallet().setBalance(walletBalanceDrawCase);
            gameTable.setBet(INITIAL_STATE);
        }
        logsService.writeGameActionLog(gameTable, GameAction.FINISH_GAME.getAction(),
                PlayingSide.PLAYER.getPlaingSide());
    }

    /**
     * Performs logic for Dealer's game, such as taking new Cards according to situational conditions
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    private void dealerGame(GameTable gameTable) {
        logger.info("Started Dealer game process");

        logsService.writeGameActionLog(gameTable, GameAction.DEAL.getAction(), PlayingSide.DEALER.getPlaingSide());

        gameTable.getDealerCards().push(gameTable.getDealerHiddenCard());
        countDealerScores(gameTable);
        investigateDealerBlackJackCase(gameTable);

        Stack<Card> dealerCards = gameTable.getDealerCards();
        int initialDealerScores = gameTable.getDealerScores();
        int dealerScores = 0;

        while (initialDealerScores < DEALER_MAX_INITIAL_SCORERS_COUNT && dealerScores != BLACK_JACK_COMBINATION) {

            logsService.writeGameActionLog(gameTable, GameAction.HIT.getAction(), PlayingSide.DEALER.getPlaingSide());

            if (dealerScores > BLACK_JACK_COMBINATION) {
                break;
            }
            dealerCards.push(gameTable.getCardDeck().pop());
            countDealerScores(gameTable);
            dealerScores = gameTable.getDealerScores();
        }
    }

    /**
     * Investigates possibility of BlackJack case for Player during Game process
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    private void investigatePlayerBlackJackCase(GameTable gameTable) {
        logger.info("Performing investigation of BlackJack Case");

        Stack<Card> dealerCards = gameTable.getDealerCards();
        dealerCards.push(gameTable.getDealerHiddenCard());
        countDealerScores(gameTable);
        if (gameTable.getDealerScores() == BLACK_JACK_COMBINATION) {
            gameTable.setGameStatus(GameStatus.DRAW.getStatus());
            double walletBalance = gameTable.getWallet().getBalance() + gameTable.getBet();
            gameTable.getWallet().setBalance(walletBalance);
            gameTable.setBet(INITIAL_STATE);

            logsService.writeGameActionLog(gameTable, GameAction.FINISH_GAME.getAction(),
                    PlayingSide.PLAYER.getPlaingSide());
            return;
        }
        gameTable.setGameStatus(GameStatus.WIN.getStatus());
        double walletBalanceBJ = gameTable.getWallet().getBalance() + gameTable.getBet() + (gameTable.getBet() * 1.5);
        gameTable.getWallet().setBalance(walletBalanceBJ);
        gameTable.setBet(INITIAL_STATE);

        logsService.writeGameActionLog(gameTable, GameAction.FINISH_GAME.getAction(),
                PlayingSide.PLAYER.getPlaingSide());
    }

    /**
     * Investigates possibility of BlackJack case for Dealer during Game process
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
    private void investigateDealerBlackJackCase(GameTable gameTable) {
        logger.info("Started investigation of Dealer's Black Jack case");

        if (gameTable.getDealerScores() == BLACK_JACK_COMBINATION
                && gameTable.getPlayerScores() != BLACK_JACK_COMBINATION) {
            gameTable.setGameStatus(GameStatus.LOOSE.getStatus());
            gameTable.setBet(INITIAL_STATE);
        }
    }

    /**
     * Checks is there Ace card among a set of Cards for checking
     * 
     * @param cards
     *            - set of Cards for checking
     * @return <b>true</b> if Cards contain Ace card and <b>false</b> if Cards don't contain Ace card
     */
    private boolean isCardsContainAce(Stack<Card> cards) {
        return (cards.contains(new Card(Rank.ACE, Suit.CLUBS)) || cards.contains(new Card(Rank.ACE, Suit.DIAMONDS))
                || cards.contains(new Card(Rank.ACE, Suit.HEARTS)) || cards.contains(new Card(Rank.ACE, Suit.SPADES)));
    }
}
