package com.fedirchyk.blackjack.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.LoggingDao;
import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.service.LogsService;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;

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

    @Override
    public Logging writeGameActionLog(GameTable gameTable, String gameAction, String plaingSide) {
        Logging logging = new Logging();
        if (gameAction.equals(GameAction.DEAL.getAction())) {
        }
        return loggingDao.save(logging);
    }

    @Override
    public List<Logging> getAllLogs(int gameId) {
        return null;
    }

}
