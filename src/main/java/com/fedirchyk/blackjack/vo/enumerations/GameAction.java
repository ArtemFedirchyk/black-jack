package com.fedirchyk.blackjack.vo.enumerations;

/**
 * Contains all possible values of action for BlackJack game
 * 
 * @author artem.fedirchyk
 * 
 */
public enum GameAction {

    START_GAME("start"), FINISH_GAME("finish"), DEAL("deal"), BET("bet"), STAND("stand"), HIT("hit");

    private String action;

    private GameAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
