package com.fedirchyk.blackjack.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.vo.GameTable;

@Component
public class GameService {

    private static Logger logger = Logger.getLogger(GameService.class);
    
    @Autowired
    private WalletDao walletDao;
    
    public Wallet getWallet(Wallet wallet){
        logger.info("getWallet()");
        walletDao.save(new GameTable(wallet).getWallet());
        return walletDao.findOne(wallet.getWalletId());
    }
}
