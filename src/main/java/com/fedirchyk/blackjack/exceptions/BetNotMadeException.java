package com.fedirchyk.blackjack.exceptions;

public class BetNotMadeException extends GeneralGameException {

    private static final long serialVersionUID = 1722375713338685600L;

    public BetNotMadeException() {
        super();
    }

    public BetNotMadeException(String message) {
        super(message);
    }
}
