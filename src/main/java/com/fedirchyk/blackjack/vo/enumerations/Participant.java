package com.fedirchyk.blackjack.vo.enumerations;

/**
 * Contains values of Role for BlackJack game
 * 
 * @author artem.fedirchyk
 * 
 */
public enum Participant {

    DEALER("dealer"), PLAYER("player");

    private String participant;

    Participant(String participent) {
        this.participant = participent;
    }

    public String getParticipant() {
        return participant;
    }
}
