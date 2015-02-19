package com.fedirchyk.blackjack.service.implementation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Game;
import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.exceptions.BetAlreadyMadeException;
import com.fedirchyk.blackjack.exceptions.BetNotMadeException;
import com.fedirchyk.blackjack.exceptions.CardsAlreadyDealtException;
import com.fedirchyk.blackjack.exceptions.HitActionNotPosibleException;
import com.fedirchyk.blackjack.exceptions.StandActionNotPosibleException;
import com.fedirchyk.blackjack.exceptions.constatnts.ExceptionConstants;
import com.fedirchyk.blackjack.service.GameService;
import com.fedirchyk.blackjack.service.LogsService;
import com.fedirchyk.blackjack.service.gameengine.GameEngine;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.GameStatus;
import com.fedirchyk.blackjack.vo.enumerations.PlayingSide;

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

    @Autowired
    private LogsService logsService;

    /**
     * {@inheritDoc}
     */
    @Override
    public GameTable makeBet(int walletId, int bet) {
        logger.info("Started process of making Bet - [" + bet + "] for Wallet ID - [" + walletId + "]");

        if (!isBetMade(walletId)) {

            GameTable gameTable = cachedGameTables.get(new Integer(walletId));
            if (gameTable.getGameStatus() != null) {
                initNewGame(gameTable);
                logsService.writeGameActionLog(gameTable, GameAction.START_GAME.getAction(),
                        PlayingSide.PLAYER.getPlaingSide());
            }

            Wallet wallet = cachedGameTables.get(new Integer(walletId)).getWallet();

            wallet.setBalance(wallet.getBalance() - bet);
            walletDao.save(wallet);

            gameTable.setWallet(wallet);
            gameTable.setBet(bet);
            gameTable.setGameStatus(GameStatus.PENDING.getStatus());
            gameTable.setGameAction(GameAction.BET.getAction());

            logger.info("Actual Game ID - [" + gameTable.getWallet().getGame().getGameId()
                    + "] for Player' Wallet ID [" + gameTable.getWallet().getWalletId() + "]");

            logsService.writeGameActionLog(gameTable, GameAction.BET.getAction(), PlayingSide.PLAYER.getPlaingSide());

            return gameTable;
        }
        throw new BetAlreadyMadeException(ExceptionConstants.BET_ALREADY_MADE);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBetMade(int walletId) {
        logger.info("Started checking is Bet made by Player with Wallet's ID - [" + walletId + "]");
        return (cachedGameTables.get(new Integer(walletId)).getBet() != INITIAL_BET_STATE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameTable dealAction(int walletId) {
        logger.info("Started process of Deal action for Player with Wallet's ID - [" + walletId + "]");
        GameTable gameTable = cachedGameTables.get(new Integer(walletId));

        if (isBetMade(walletId)) {
            if (gameTable.getGameAction().equals(GameAction.BET.getAction())) {
                gameTable.setGameAction(GameAction.DEAL.getAction());

                gameEngine.hangOutCardsDeck(gameTable.getCardDeck());
                gameEngine.dealCards(gameTable);
                gameEngine.countPlayerScores(gameTable);
                gameEngine.investigateGame(gameTable, GameAction.START_GAME.getAction());

                logsService.writeGameActionLog(gameTable, GameAction.DEAL.getAction(),
                        PlayingSide.PLAYER.getPlaingSide());

                return gameTable;
            }
            throw new CardsAlreadyDealtException(ExceptionConstants.CARDS_ALREADY_DEALT);
        }
        throw new BetNotMadeException(ExceptionConstants.BET_NOT_MADE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameTable hitAction(int walletId) {
        logger.info("Started performing of Hit action for Player with Wallet's ID - [" + walletId + "]");
        GameTable gameTable = cachedGameTables.get(new Integer(walletId));

        if (gameTable.getGameStatus().equals(GameStatus.PENDING.getStatus())) {
            if ((gameTable.getGameAction().equals(GameAction.DEAL.getAction()) || gameTable.getGameAction().equals(
                    GameAction.HIT.getAction()))) {
                gameTable.setGameAction(GameAction.HIT.getAction());
                gameEngine.investigateGame(gameTable, GameAction.HIT.getAction());

                // TODO: Check is this case for saving is useful
                if (gameTable.getGameStatus().equals(GameStatus.LOOSE)) {
                    walletDao.save(gameTable.getWallet());
                }

                logsService.writeGameActionLog(gameTable, GameAction.HIT.getAction(),
                        PlayingSide.PLAYER.getPlaingSide());

                return gameTable;
            }
            throw new HitActionNotPosibleException(ExceptionConstants.HIT_ACTION_IS_NOT_POSSIBLE_WRONG_ACTION);
        }
        throw new HitActionNotPosibleException(ExceptionConstants.HIT_ACTION_IS_NOT_POSSIBLE_WRONG_STATUS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameTable standAction(int walletId) {
        logger.info("Started performing of Stand Action for Player wit Wallet's ID -[" + walletId + "]");
        GameTable gameTable = cachedGameTables.get(new Integer(walletId));

        if (gameTable.getGameStatus().equals(GameStatus.PENDING.getStatus())) {
            if (gameTable.getGameAction().equals(GameAction.DEAL.getAction())
                    || gameTable.getGameAction().equals(GameAction.HIT.getAction())) {

                gameTable.setGameAction(GameAction.STAND.getAction());

                logsService.writeGameActionLog(gameTable, GameAction.STAND.getAction(),
                        PlayingSide.PLAYER.getPlaingSide());

                gameEngine.investigateGame(gameTable, gameTable.getGameAction());
                walletDao.save(gameTable.getWallet());

                return gameTable;
            }
            throw new StandActionNotPosibleException(ExceptionConstants.STAND_ACTION_IS_NOT_POSSIBLE_WRONG_ACTION);
        }
        throw new StandActionNotPosibleException(ExceptionConstants.STAND_ACTION_IS_NOT_POSSIBLE_WRONG_STATUS);
    }

    /**
     * Initializes new Game for current Player's Wallet, during process of Bet making. Performs only when previous Game
     * is finished.
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     */
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
