package com.fedirchyk.blackjack.service.gameengine;

import java.util.Collections;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fedirchyk.blackjack.vo.Card;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.GameStatus;
import com.fedirchyk.blackjack.vo.enumerations.Rank;
import com.fedirchyk.blackjack.vo.enumerations.Suit;

@Component
public class GameEngine {

    private static Logger logger = Logger.getLogger(GameEngine.class);

    private static final int BLACK_JACK_COMBINATION = 21;

    private static final int DEALER_MAX_HITCARDS_SCORERS_COUNT = 17;

    private static final int MAX_HIT_CARDS_SCORES_COUNT = 10;

    private static final int INITIAL_STATE = 0;

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
        gameTable.setGameStatus(GameStatus.PENDING.getStatus());
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
        if (playerScores > BLACK_JACK_COMBINATION && playerCards.contains(new Card(Rank.ACE, Suit.DIAMONDS))) {
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
        if (dealerScores > BLACK_JACK_COMBINATION && dealerCards.contains(new Card(Rank.ACE, Suit.DIAMONDS))) {
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
            investigateBlackJackCase(gameTable);
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
        gameTable.setBet(INITIAL_STATE);
        gameTable.setPlayerScores(INITIAL_STATE);
        gameTable.setDealerScores(INITIAL_STATE);
        gameTable.setCardDeck(new Stack<Card>());
        gameTable.setDealerCards(new Stack<Card>());
        gameTable.setPlayerCards(new Stack<Card>());
        gameTable.setGameStatus(null);
        gameTable.setGameAction(null);
    }

    private void investigateBlackJackCase(GameTable gameTable) {
        logger.info("Performing investigation of BlackJack Case");
        Stack<Card> dealerCards = gameTable.getDealerCards();
        dealerCards.push(gameTable.getDealerHiddenCard());
        countDealerScores(gameTable);
        if (gameTable.getDealerScores() == BLACK_JACK_COMBINATION) {
            gameTable.setGameStatus(GameStatus.DRAW.getStatus());
            double walletBalance = gameTable.getWallet().getBalance() + gameTable.getBet();
            gameTable.getWallet().setBalance(walletBalance);
        }
        gameTable.setGameStatus(GameStatus.WIN.getStatus());
        double walletBalanceBJ = gameTable.getWallet().getBalance() + gameTable.getBet() + (gameTable.getBet() * 1.5);
        gameTable.getWallet().setBalance(walletBalanceBJ);
        gameTable.setBet(INITIAL_STATE);
    }

    private void hitAction(GameTable gameTable) {
        logger.info("Performing of HIT action (low level)");
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

    private void standAction(GameTable gameTable) {
        logger.info("Performing of STAND action (low level)");
        dealerGame(gameTable);

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
    }

    private void dealerGame(GameTable gameTable) {
        logger.info("Started Dealer game process");

        gameTable.getDealerCards().push(gameTable.getDealerHiddenCard());

        countDealerScores(gameTable);
        int dealerScores = gameTable.getDealerScores();

        if (dealerScores == BLACK_JACK_COMBINATION && gameTable.getPlayerScores() != BLACK_JACK_COMBINATION) {
            gameTable.setGameStatus(GameStatus.LOOSE.getStatus());
            gameTable.setBet(INITIAL_STATE);
        }

        Stack<Card> dealerCards = gameTable.getDealerCards();

        if (dealerScores < DEALER_MAX_HITCARDS_SCORERS_COUNT || dealerScores != BLACK_JACK_COMBINATION) {
            dealerCards.push(gameTable.getCardDeck().pop());
            countDealerScores(gameTable);
        }

        // while (dealerScores < DEALER_MAX_HITCARDS_SCORERS_COUNT || dealerScores == BLACK_JACK_COMBINATION) {
        // if (dealerScores > BLACK_JACK_COMBINATION)
        // break;
        // gameTable.getDealerCards().push(gameTable.getCardDeck().pop());
        // countDealerScores(gameTable);
        // }
    }
}
