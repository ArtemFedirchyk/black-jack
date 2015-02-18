package com.fedirchyk.blackjack.vo.enumerations;

/**
 * Contains all possible values of Account action for BlackJack game
 * 
 * @author artem.fedirchyk
 * 
 */
public enum AccountAction {

    PLAYER_INITIALIZATION("player initialization"), BALANCE_RECHARGE("balance recharge");

    private String accountAction;

    private AccountAction(String accountAction) {
        this.accountAction = accountAction;
    }

    public String getAccountAction() {
        return accountAction;
    }

}
