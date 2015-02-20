package com.fedirchyk.blackjack.dao;

import org.springframework.data.repository.CrudRepository;

import com.fedirchyk.blackjack.entity.Wallet;

/**
 * Provides access to Wallet entity (contains logic for CRUD operations for storing and reading information in/from DB)
 * 
 * @author artem.fedirchyk
 * 
 */
public interface WalletDao extends CrudRepository<Wallet, Integer> {

}
