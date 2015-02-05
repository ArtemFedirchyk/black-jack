package com.fedirchyk.blackjack.dao;

import org.springframework.data.repository.CrudRepository;

import com.fedirchyk.blackjack.entity.Logging;

/**
 * Provides access to Logging entity (contains logic for CRUD operations for storing information in DB)
 * 
 * @author artem.fedirchyk
 * 
 */
public interface LoggingDao extends CrudRepository<Logging, Integer> {

}
