package com.fedirchyk.blackjack.exceptions;

public class BetAlreadyMadeException extends GeneralGameException {

    private static final long serialVersionUID = -7156493635023528904L;

    public BetAlreadyMadeException() {
        super();
    }

    public BetAlreadyMadeException(String message) {
        super(message);
    }
}
