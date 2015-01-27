package com.fedirchyk.blackjack.vo.enumerations;

public enum GameAction {

    START_GAME("start"), FINISH_GAME("finish"), DEAL("deal"), BET("bet"), STAND("stand"), HIT("hit"), REPLENISH(
            "replenish");
    
    private String action;
    
    private GameAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
