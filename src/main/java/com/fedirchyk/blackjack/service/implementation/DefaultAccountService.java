package com.fedirchyk.blackjack.service.implementation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.vo.GameTable;

@Service
public class DefaultAccountService implements AccountService {

    @Autowired
    private WalletDao walletDao;

    @Override
    public GameTable initializePlayer(double balance) {
        Wallet wallet = new Wallet();
        wallet.setBalance(balance);
        wallet.setTime(new Date());
        
        Wallet savedWallet = walletDao.save(wallet);

        return new GameTable(savedWallet);
    }

}
