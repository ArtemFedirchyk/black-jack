package com.fedirchyk.blackjack.vo.enumerations;

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
