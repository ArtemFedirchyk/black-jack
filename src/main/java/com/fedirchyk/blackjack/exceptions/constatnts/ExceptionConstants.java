package com.fedirchyk.blackjack.exceptions.constatnts;

/**
 * Contains constant values of custom exceptions' messages
 * 
 * @author artem.fedirchyk
 * 
 */
public class ExceptionConstants {

    public static final String BET_NOT_MADE = "First need to make Bet before DEAL action";

    public static final String WALLET_NOT_FOUND = "Wallet with such ID not found in DataBase";

    public static final String WALLET_BALANCE_NOT_ENOUGH = "The balance of Player's Wallet is not enough for making BET action with this Bet value";

}
