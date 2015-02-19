package com.fedirchyk.blackjack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fedirchyk.blackjack.entity.Logging;

/**
 * Provides access to Logging entity (contains logic for CRUD operations for storing information in DB)
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
    @Query(value = "SELECT * FROM logging WHERE game_id=:game_id", nativeQuery = true)
    List<Logging> getLogsForSpecifiedGame(@Param("game_id") int gameId);
}
