package com.fedirchyk.blackjack.dao;

import org.springframework.data.repository.CrudRepository;

import com.fedirchyk.blackjack.entity.Game;


/**
 * Provides access to Game entity (contains logic for CRUD operations for storing and reading information in/from DB)
 * 
 * @author artem.fedirchyk
 * 
 */
public interface GameDao extends CrudRepository<Game, Integer> {

}
