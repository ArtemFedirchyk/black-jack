package com.fedirchyk.blackjack.exceptions;

public class GameNotFoundException extends GeneralGameException {

    private static final long serialVersionUID = 71261003285966384L;

    public GameNotFoundException() {
        super();
    }

    public GameNotFoundException(String message) {
        super(message);
    }
}
