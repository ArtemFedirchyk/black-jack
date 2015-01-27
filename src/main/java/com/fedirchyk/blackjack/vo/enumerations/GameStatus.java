package com.fedirchyk.blackjack.vo.enumerations;

public enum GameStatus {

    WIN("win"), LOOSE("loose"), PENDING("pending"), DRAW("draw");

    private String status;

    private GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
