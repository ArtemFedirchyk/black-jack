package com.fedirchyk.blackjack.service;

import com.fedirchyk.blackjack.entity.Wallet;

public interface GameService {

    void saveWallet(Wallet entity);

    Wallet getWallet(Integer id);

    boolean isWalletExist(Integer id);
}
