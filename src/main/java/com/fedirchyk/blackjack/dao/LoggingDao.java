package com.fedirchyk.blackjack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fedirchyk.blackjack.entity.Logging;

/**
 * Provides access to Logging entity (contains logic for CRUD operations for storing and reading information in/from DB)
 * 
 * @author artem.fedirchyk
 * 
 */
public interface LoggingDao extends CrudRepository<Logging, Integer> {

    /**
     * Finds in DB all logs, which is related to specified Game.
     * 
     * @param gameId
     *            - value of Game ID, which will be used for finding needed log in DB
     * @return list of {@link Logging} type objects
     */
    @Query(value = "SELECT * FROM LOGGING WHERE GAME_ID=:GAME_ID", nativeQuery = true)
    List<Logging> getLogsForSpecifiedGame(@Param("GAME_ID") int gameId);

    /**
     * Finds in DB all logs, which is related to specified Player.
     * 
     * @param walletId
     *            - value of Wallet's ID of specified Player, which will be used for finding needed log in DB
     * @return list of {@link Logging} type objects
     */
    @Query(value = "SELECT * FROM LOGGING WHERE WALLET_ID=:WALLET_ID", nativeQuery = true)
    List<Logging> getLogsForSpecifiedPlayer(@Param("WALLET_ID") int walletId);
}
