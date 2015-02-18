package com.fedirchyk.blackjack.vo.enumerations;

/**
 * Contains values of Role for BlackJack game
 * 
 * @author artem.fedirchyk
 * 
 */
public enum PlayingSide {

    DEALER("dealer"), PLAYER("player");

    private String playingSide;

    PlayingSide(String playingSide) {
        this.playingSide = playingSide;
    }

    public String getPlaingSide() {
        return playingSide;
    }
}
