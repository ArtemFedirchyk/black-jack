package com.fedirchyk.blackjack.vo.enumerations;

/**
 * Contains information about types of operations, which are used in BlackJack Game
 * 
 * @author artem.fedirchyk
 * 
 */
public enum OperationType {

    GAME_OPERATION("game operation"), ACCOUNT_OPERATION("account operation");

    private String operationType;

    private OperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }

}
