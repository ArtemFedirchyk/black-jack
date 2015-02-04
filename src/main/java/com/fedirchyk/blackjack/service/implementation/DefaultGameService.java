package com.fedirchyk.blackjack.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.service.GameService;

@Service
public class DefaultGameService implements GameService {

    private static Logger logger = Logger.getLogger(DefaultGameService.class);

    @Autowired
    private WalletDao walletDao;
    
    @Override
    public void saveWallet(Wallet entity) {
        walletDao.save(entity);
    }

    @Override
    public Wallet getWallet(Integer id) {
        logger.info("getWallet()");
        return walletDao.findOne(id);
    }

    @Override
    public boolean isWalletExist(Integer id) {
        logger.info("Checking is wallet with - [" + id + "] exist in DB");
        return walletDao.exists(id);
    }
}
