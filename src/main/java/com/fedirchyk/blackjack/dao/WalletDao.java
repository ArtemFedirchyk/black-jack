package com.fedirchyk.blackjack.dao;

import org.springframework.data.repository.CrudRepository;

import com.fedirchyk.blackjack.entity.Wallet;

/**
 * Provides access to Wallet entity (contains logic for CRUD operations for storing information in DB)
 * 
 * @author artem.fedirchyk
 * 
 */
public interface WalletDao extends CrudRepository<Wallet, Integer> {

}
