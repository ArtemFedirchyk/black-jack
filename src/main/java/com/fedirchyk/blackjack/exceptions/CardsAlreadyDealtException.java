package com.fedirchyk.blackjack.exceptions;

public class CardsAlreadyDealtException extends GeneralGameException {

    private static final long serialVersionUID = -7146977553529402284L;

    public CardsAlreadyDealtException() {
        super();
    }

    public CardsAlreadyDealtException(String message) {
        super(message);
    }

}
