package com.fedirchyk.blackjack.dao;

import org.springframework.data.repository.CrudRepository;

import com.fedirchyk.blackjack.entity.Wallet;

public interface WalletDao extends CrudRepository<Wallet, Integer> {

}
