package com.fedirchyk.blackjack.service.implementation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedirchyk.blackjack.dao.WalletDao;
import com.fedirchyk.blackjack.entity.Game;
import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.service.LogsService;
import com.fedirchyk.blackjack.vo.GameTable;
import com.fedirchyk.blackjack.vo.enumerations.AccountAction;
import com.fedirchyk.blackjack.vo.enumerations.GameAction;
import com.fedirchyk.blackjack.vo.enumerations.PlayingSide;

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

    @Autowired
    private LogsService logsService;

    /**
     * {@inheritDoc}
     */
    @Override
    public GameTable initializePlayer(double balance) {
        logger.info("Started initialising Player in BlacJack game process");
        Wallet savedWallet = saveNewWallet(balance);
        GameTable gameTable = new GameTable(savedWallet);
        DefaultGameService.cachedGameTables.put(new Integer(savedWallet.getWalletId()), gameTable);

        logsService.writeAccountActionLog(gameTable, AccountAction.PLAYER_INITIALIZATION.getAccountAction());
        logsService
                .writeGameActionLog(gameTable, GameAction.START_GAME.getAction(), PlayingSide.PLAYER.getPlaingSide());

        return gameTable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameTable increaseWalletsBalance(int walletId, double increaseCount) {
        logger.info("Increasing balance for Wallet - ID [" + walletId + "], increase count is - [" + increaseCount
                + "]");
        Wallet wallet = walletDao.findOne(walletId);
        wallet.setBalance(wallet.getBalance() + increaseCount);
        walletDao.save(wallet);

        GameTable gameTable = DefaultGameService.cachedGameTables.get(new Integer(walletId));
        gameTable.setWallet(wallet);

        logsService.writeAccountActionLog(gameTable, AccountAction.BALANCE_RECHARGE.getAccountAction());
        
        return gameTable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWalletExist(int walletId) {
        logger.info("Started process of checking is Player's Wallet with ID - [" + walletId + "] exist in DB");
        return walletDao.exists(walletId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerBalanceEnough(int walletId, double bet) {
        logger.info("Checking is balane of Wallet - ID [" + walletId + "] is enough for bet - [" + bet + "]");
        return (walletDao.findOne(walletId).getBalance() >= bet);
    }

    /**
     * Saves new Wallet in DB with incoming value of Wallet's balance and with initial Game
     * 
     * @param balance
     *            - value of balance, which will be saved as Wallet's initial balance
     * @return - object of saved Wallet in DB (Wallet Entity)
     */
    private Wallet saveNewWallet(double balance) {
        logger.info("Saving Player's Wallet in DB with balance - [" + balance + "]");
        Wallet wallet = new Wallet();
        Game game = new Game();
        game.setWallet(wallet);
        wallet.setGame(game);
        wallet.setBalance(balance);
        wallet.setTime(new Date());

        return walletDao.save(wallet);
    }
}
