package com.fedirchyk.blackjack.service;

import com.fedirchyk.blackjack.vo.GameTable;

/**
 * Contains all functional logic associated with Game process
 * 
 * @author artem.fedirchyk
 * 
 */
public interface GameService {

    /**
     * Performs main functional logic for <b>make Bet</b> action
     * 
     * @param walletId
     *            - value of Wallet ID, whose owner makes Bet
     * @param bet
     *            - count of coins for bet
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    GameTable makeBet(int walletId, int bet);

    /**
     * Checks is <b>Bet</b> made by Player
     * 
     * @return <b>true</b> if Bet is made and <b>false</b> if Bet isn't made
     */
    boolean isBetMade(int walletId);

    /**
     * Performs main functional logic for <b>Deal</b> action, also this method starts general Game process
     * 
     * @param walletId
     *            - value of Wallet ID, whose owner will be Player of this current Game
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    GameTable dealAction(int walletId);

    /**
     * Performs main functional logic for <b>Hit</b> action, which assumes next step in the Game process by Player
     * 
     * @param walletId
     *            - value of Wallet ID, whose owner will be Player of this current Game
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    GameTable hitAction(int walletId);

    /**
     * Performs main functional logic for <b>Stand</b> action, which assumes final step in the Game process by Player
     * and starting game process for Dealer
     * 
     * @param walletId
     *            - value of Wallet ID, whose owner will be Player of this current Game
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    GameTable standAction(int walletId);
}
