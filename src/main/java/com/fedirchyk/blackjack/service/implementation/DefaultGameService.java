package com.fedirchyk.blackjack.service.implementation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Game;
import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.exceptions.CardsAlreadyDealtException;
import com.fedirchyk.blackjack.exceptions.constatnts.ExceptionConstants;
import com.fedirchyk.blackjack.service.GameService;
import com.fedirchyk.blackjack.service.gameengine.GameEngine;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.GameStatus;

/**
 * Contains implementation of all functional logic associated with Game process
 * 
 * @author artem.fedirchyk
 * 
 */
@Service
public class DefaultGameService implements GameService {

    private static Logger logger = Logger.getLogger(DefaultGameService.class);

    public static ConcurrentMap<Integer, GameTable> cachedGameTables = new ConcurrentHashMap<Integer, GameTable>();

    private static final int INITIAL_BET_STATE = 0;

    @Autowired
    private WalletDao walletDao;

    @Autowired
    private GameEngine gameEngine;

    @Override
    public GameTable makeBet(int walletId, int bet) {
        logger.info("Started process of making Bet - [" + bet + "] for Wallet ID - [" + walletId + "]");

        GameTable gameTable = cachedGameTables.get(new Integer(walletId));
        if (gameTable.getGameStatus() != null) {
            initNewGame(gameTable);
        }

        Wallet wallet = cachedGameTables.get(new Integer(walletId)).getWallet();

        wallet.setBalance(wallet.getBalance() - bet);
        walletDao.save(wallet);

        gameTable.setWallet(wallet);
        gameTable.setBet(bet);
        gameTable.setGameStatus(GameStatus.PENDING.getStatus());
        gameTable.setGameAction(GameAction.BET.getAction());

        return gameTable;
    }

    @Override
    public boolean isBetMade(int walletId) {
        logger.info("Started checking is Bet made by Player with Wallet's ID - [" + walletId + "]");
        return (cachedGameTables.get(new Integer(walletId)).getBet() != INITIAL_BET_STATE);
    }

    @Override
    public GameTable dealAction(int walletId) {
        logger.info("Started process of Deal action for Player with Wallet's ID - [" + walletId + "]");
        GameTable gameTable = cachedGameTables.get(new Integer(walletId));

        if (gameTable.getGameAction().equals(GameAction.BET.getAction())) {
            gameEngine.hangOutCardsDeck(gameTable.getCardDeck());
            gameEngine.dealCards(gameTable);
            gameEngine.countPlayerScores(gameTable);
            gameEngine.investigateGame(gameTable, GameAction.START_GAME.getAction());

            if (gameTable.getBet() == INITIAL_BET_STATE) {
                walletDao.save(gameTable.getWallet());
            }

            gameTable.setGameAction(GameAction.DEAL.getAction());

            return gameTable;
        }
        throw new CardsAlreadyDealtException(ExceptionConstants.CARDS_ALREADY_DEALT);
    }

    @Override
    public GameTable hitAction(int walletId) {
        logger.info("Started performing of Hit action for Player with Wallet's ID - [" + walletId + "]");
        GameTable gameTable = cachedGameTables.get(new Integer(walletId));
        gameTable.setGameAction(GameAction.HIT.getAction());

        gameEngine.investigateGame(gameTable, GameAction.HIT.getAction());

        if (gameTable.getGameStatus().equals(GameStatus.LOOSE)) {
            walletDao.save(gameTable.getWallet());
        }

        return gameTable;
    }

    @Override
    public GameTable standAction(int walletId) {
        GameTable gameTable = cachedGameTables.get(new Integer(walletId));
        gameTable.setGameAction(GameAction.STAND.getAction());

        gameEngine.investigateGame(gameTable, GameAction.STAND.getAction());

        if (gameTable.getGameStatus().equals(GameStatus.DRAW.getStatus())) {
            walletDao.save(gameTable.getWallet());
        }

        return gameTable;
    }

    private void initNewGame(GameTable gameTable) {
        logger.info("Initialization of new Game for Player's Wallet ID - [" + gameTable.getWallet().getWalletId() + "]");
        gameEngine.destroyGame(gameTable);
        Game game = new Game();
        Wallet wallet = gameTable.getWallet();
        game.setWallet(wallet);
        wallet.setGame(game);
        gameTable.setWallet(walletDao.save(wallet));
    }

}
