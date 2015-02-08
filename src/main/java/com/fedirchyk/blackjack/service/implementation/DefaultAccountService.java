package com.fedirchyk.blackjack.service.implementation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.vo.GameTable;

/**
 * Contains implementation of logic associated with Player
 * 
 * @author artem.fedirchyk
 * 
 */
@Service
public class DefaultAccountService implements AccountService {

    private static Logger logger = Logger.getLogger(DefaultAccountService.class);

    @Autowired
    private WalletDao walletDao;

    @Override
    public GameTable initializePlayer(double balance) {
        Wallet wallet = new Wallet();
        wallet.setBalance(balance);
        wallet.setTime(new Date());

        return new GameTable(walletDao.save(wallet));
    }

    @Override
    public boolean isWalletExist(int walletId) {
        logger.info("Started process of checking is Player's Wallet with ID - [" + walletId + "] exist in DB");
        return walletDao.exists(walletId);
    }

    @Override
    public GameTable increaseWalletsBalance(int walletId, double increaseCount) {
        Wallet wallet = walletDao.findOne(walletId);
        wallet.setBalance(wallet.getBalance() + increaseCount);

        return new GameTable(walletDao.save(wallet));
    }

    @Override
    public boolean isPlayerBalanceEnough(int walletId, double bet) {
        return (walletDao.findOne(walletId).getBalance() >= bet);
    }
}
