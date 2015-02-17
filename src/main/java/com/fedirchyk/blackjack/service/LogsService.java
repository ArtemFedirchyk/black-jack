package com.fedirchyk.blackjack.service;

import java.util.List;

import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.vo.GameTable;

/**
 * Contains all functional logic associated with Logs writing process
 * 
 * @author artem.fedirchyk
 * 
 */
public interface LogsService {

    /**
     * Writes log which is related to some of Game actions, such as 'StartGame', 'Bet', 'Deal', 'Hit', 'Stand' or
     * 'Finish'. This record goes to DB.
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     * @return - object of {@link Logging} type, which contains information about last record to DB, which is related to
     *         one of the above game actions
     */
    Logging writeGameActionLog(GameTable gameTable, String gameAction, String plaingSide);

    List<Logging> getAllLogs(int gameId);

}
