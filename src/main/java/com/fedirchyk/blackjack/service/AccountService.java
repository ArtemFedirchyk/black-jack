package com.fedirchyk.blackjack.service;

import com.fedirchyk.blackjack.vo.GameTable;

/**
 * Contains all logic associated with Player
 * 
 * @author artem.fedirchyk
 * 
 */
public interface AccountService {

    /**
     * Performs the process of initialization of new Player in BlackJack game
     * 
     * @param balance
     *            - value of balance, which will be initial for new Player's Wallet
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    GameTable initializePlayer(double balance);

    /**
     * Checks is Player's Wallet exists in DB
     * 
     * @param walletId
     *            - value of Wallet's ID for checking
     * @return - true if Wallet exists in DB and false if it doesn't exist
     */
    boolean isWalletExist(int walletId);

    /**
     * Increases balance for needed Wallet
     * 
     * @param walletId
     *            - value of Wallet's ID whose balance will be increased
     * @param increaseCount
     *            - value of incoming count which will be used for increasing of existing Wallet's balance
     * @return - Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    GameTable increaseWalletsBalance(int walletId, double increaseCount);

    /**
     * Checks is Wallet's balance enough for making <b>Bet</b> action
     * 
     * @param walletId
     *            - value of Wallet's ID whose balance will be checked
     * @param bet
     *            - value of estimated Bet
     * @return - <b>true</b> if balance is enough and <b>false</b> if it isn't enough
     */
    boolean isPlayerBalanceEnough(int walletId, double bet);
}
