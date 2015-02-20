package com.fedirchyk.blackjack.service;

import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.Logs;

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
     * @param gameAction
     *            - value of some Game Action, which will be logged
     * @param playingSide
     *            - one of member of Game process, it could be Player or Dealer
     * @return - object of {@link Logging} type, which contains information about last record to DB, which is related to
     *         one of the above game actions
     */
    Logging writeGameActionLog(GameTable gameTable, String gameAction, String playingSide);

    /**
     * Writes log which is related to some of Account actions, such as 'Player initialization' or 'Balance recharge'.
     * This record goes to DB.
     * 
     * @param gameTable
     *            - Object of {@link GameTable} type, which contains all information about Player's wallet and Game
     *            state
     * @param accountAction
     *            - value of some Account Action, which will be logged
     * 
     * @return - object of {@link Logging} type, which contains information about last record to DB, which is related to
     *         one of the above game actions
     */
    Logging writeAccountActionLog(GameTable gameTable, String accountAction);

    /**
     * Gives all Logs from DB for some specified Game
     * 
     * @param gameId
     *            - value of Game ID, which will be used for searching needed Logs
     * @return Object of {@link Logs} type, which contain logging information related to specified Game
     */
    Logs getAllLogsForSpecifiedGame(int gameId);

    /**
     * Gives all Logs from DB for some specified Player
     * 
     * @param walletId
     *            - value of Wallet's ID of specified Player, which will be used for finding needed log in DB
     * @return Object of {@link Logs} type, which contain logging information related to specified Player
     */
    Logs getAllLogsForSpecifiedPlayer(int walletId);

}
