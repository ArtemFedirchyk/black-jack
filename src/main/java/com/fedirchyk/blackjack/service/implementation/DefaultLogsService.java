package com.fedirchyk.blackjack.service.implementation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fedirchyk.blackjack.dao.LoggingDao;
import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.service.LogsService;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.Logs;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.LogsType;
import com.fedirchyk.blackjack.vo.enumerations.OperationType;
import com.fedirchyk.blackjack.vo.enumerations.PlayingSide;

/**
 * Contains implementation of functional logic associated with Logs writing process
 * 
 * @author artem.fedirchyk
 * 
 */
@Service
public class DefaultLogsService implements LogsService {

    private static Logger logger = Logger.getLogger(DefaultLogsService.class);

    @Autowired
    private LoggingDao loggingDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Logging writeGameActionLog(GameTable gameTable, String gameAction, String playingSide) {
        Logging logging = new Logging();

        logger.info("Started process of writing Logs for Game action case");

        logging.setGame(gameTable.getWallet().getGame());
        logging.setWallet(gameTable.getWallet());
        logging.setGameStatus(gameTable.getGameStatus());
        logging.setOperationType(OperationType.GAME_OPERATION.getOperationType());
        logging.setPlayingSide(playingSide);
        logging.setTime(new Date().toString());
        writeLogDifferentPlayingSideCase(gameTable, playingSide, logging);
        if (gameAction.equals(GameAction.START_GAME.getAction())) {
            logging.setOperation(GameAction.START_GAME.getAction());
        }
        if (gameAction.equals(GameAction.BET.getAction())) {
            logging.setOperation(GameAction.BET.getAction());
        }
        if (gameAction.equals(GameAction.DEAL.getAction())) {
            logging.setOperation(GameAction.DEAL.getAction());
        }
        if (gameAction.equals(GameAction.HIT.getAction())) {
            logging.setOperation(GameAction.HIT.getAction());
            writeLogHitActionCase(gameTable, playingSide, logging);
        }
        if (gameAction.equals(GameAction.STAND.getAction())) {
            logging.setOperation(GameAction.STAND.getAction());
        }
        if (gameAction.equals(GameAction.FINISH_GAME.getAction())) {
            logging.setOperation(GameAction.FINISH_GAME.getAction());
        }
        return loggingDao.save(logging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Logging writeAccountActionLog(GameTable gameTable, String accountAction) {
        Logging logging = new Logging();

        logger.info("Started process of writing Logs for Account's action case");

        logging.setGame(gameTable.getWallet().getGame());
        logging.setWallet(gameTable.getWallet());
        logging.setOperationType(OperationType.ACCOUNT_OPERATION.getOperationType());
        logging.setPlayingSide(PlayingSide.PLAYER.getPlaingSide());
        logging.setOperation(accountAction);
        logging.setTime(new Date().toString());
        return loggingDao.save(logging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logs getAllLogsForSpecifiedGame(int gameId) {
        Logs logs = new Logs();
        logs.setLogType(LogsType.LOGS_OF_GAME_TYPE.getLogType());
        logs.setLogs(loggingDao.getLogsForSpecifiedGame(gameId));
        return logs;
    }

    @Override
    public Logs getAllLogsForSpecifiedPlayer(int walletId) {
        Logs logs = new Logs();
        logs.setLogType(LogsType.LOGS_OFACCOUNT_TYPE.getLogType());
        logs.setLogs(loggingDao.getLogsForSpecifiedPlayer(walletId));
        return logs;
    }

    /**
     * {@inheritDoc}
     */
    private void writeLogDifferentPlayingSideCase(GameTable gameTable, String playingSide, Logging logging) {
        if (playingSide.equals(PlayingSide.PLAYER.getPlaingSide())) {
            logging.setCommonScoresCount(gameTable.getPlayerScores());
        } else if (playingSide.equals(PlayingSide.DEALER.getPlaingSide())) {
            logging.setCommonScoresCount(gameTable.getDealerScores());
        }
    }

    private void writeLogHitActionCase(GameTable gameTable, String playingSide, Logging logging) {
        if (playingSide.equals(PlayingSide.PLAYER.getPlaingSide())) {
            logging.setCardScoresValue(gameTable.getPlayerCards().peek().getRank().getCardValue());
        } else if (playingSide.equals(PlayingSide.DEALER.getPlaingSide())) {
            logging.setCardScoresValue(gameTable.getDealerCards().peek().getRank().getCardValue());
        }
    }
}
