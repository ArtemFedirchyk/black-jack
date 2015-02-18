package com.fedirchyk.blackjack.service.implementation;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.LoggingDao;
import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.service.LogsService;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
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

    @Autowired
    private LoggingDao loggingDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Logging writeGameActionLog(GameTable gameTable, String gameAction, String playingSide) {
        Logging logging = new Logging();

        logging.setGame(gameTable.getWallet().getGame());
        logging.setOperationType(OperationType.GAME_OPERATION.getOperationType());
        logging.setTime(new Date());
        if (gameAction.equals(GameAction.START_GAME.getAction())) {
            logging.setPlayingSide(playingSide);
            logging.setOperation(GameAction.START_GAME.getAction());
        }
        if (gameAction.equals(GameAction.BET.getAction())) {

        }
        if (gameAction.equals(GameAction.DEAL.getAction())) {
            writeLogDealActionCase(gameTable, playingSide, logging);
        }
        if (gameAction.equals(GameAction.HIT.getAction())) {

        }
        if (gameAction.equals(GameAction.STAND.getAction())) {

        }
        if (gameAction.equals(GameAction.FINISH_GAME.getAction())) {

        }
        return loggingDao.save(logging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logging writeAccountActionLog(GameTable gameTable, String accountAction) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Logging> getAllLogs(int gameId) {
        List<Logging> logs = new LinkedList<Logging>();
        Iterable<Logging> logsFromDb = loggingDao.findAll();
        for (Logging log : logsFromDb) {
            logs.add(log);
        }
        return logs;
    }

    private void writeLogDealActionCase(GameTable gameTable, String playingSide, Logging logging) {
        if (playingSide.equals(PlayingSide.PLAYER.getPlaingSide())) {
            logging.setCardScoresValue(gameTable.getPlayerCards().peek().getRank().getCardValue());
        } else if (playingSide.equals(PlayingSide.DEALER.getPlaingSide())) {
            logging.setCardScoresValue(gameTable.getDealerCards().peek().getRank().getCardValue());
        }
    }
}
