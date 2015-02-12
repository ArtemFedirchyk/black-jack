package com.fedirchyk.blackjack.exceptions.constatnts;

/**
 * Contains constant values of custom exceptions' messages
 * 
 * @author artem.fedirchyk
 * 
 */
public class ExceptionConstants {

    public static final String BET_NOT_MADE = "First need to make Bet before DEAL action";

    public static final String BET_ALREADY_MADE = "Bet already made for this Game, another Bet is not allowed";

    public static final String WALLET_NOT_FOUND = "Wallet with such ID not found in DataBase";

    public static final String WALLET_BALANCE_NOT_ENOUGH = "The balance of Player's Wallet is not enough for making BET action with this Bet value";

    public static final String CARDS_ALREADY_DEALT = "Cards already dealt and you couldn't take new deal before game's finish";

    public static final String HIT_ACTION_IS_NOT_POSSIBLE_WRONG_ACTION = "Hit action is possbile only after Deal or Hit actions, otherwise Hit action isn't possible";

    public static final String HIT_ACTION_IS_NOT_POSSIBLE_WRONG_STATUS = "Hit action isn't possbile after finishing Game's process, start new game before this action";

    public static final String STAND_ACTION_IS_NOT_POSSIBLE_WRONG_ACTION = "Stand action is possbile only after Deal or Hit actions, otherwise Stand action isn't possible";

    public static final String STAND_ACTION_IS_NOT_POSSIBLE_WRONG_STATUS = "Stand action isn't possible after finishing Game's process, start new game before this action";

}
