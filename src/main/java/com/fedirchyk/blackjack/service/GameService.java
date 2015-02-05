package com.fedirchyk.blackjack.service;

import com.fedirchyk.blackjack.entity.Wallet;

/**
 * Contains all functional logic associated with Game process
 * 
 * @author artem.fedirchyk
 * 
 */
public interface GameService {

    void saveWallet(Wallet entity);

    Wallet getWallet(Integer id);

    boolean isWalletExist(Integer id);
}
