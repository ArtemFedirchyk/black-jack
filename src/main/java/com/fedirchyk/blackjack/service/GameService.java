package com.fedirchyk.blackjack.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Wallet;

@Component
public class GameService {

    private static Logger logger = Logger.getLogger(GameService.class);

    @Autowired
    private WalletDao walletDao;

    public void saveWallet(Wallet entity) {
        walletDao.save(entity);
    }

    public Wallet getWallet(Integer id) {
        logger.info("getWallet()");
        return walletDao.findOne(id);
    }

    public boolean isWalletExist(Integer id) {
        logger.info("Checking is wallet with - [" + id + "] exist in DB");
        return walletDao.exists(id);
    }
}
