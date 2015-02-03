package com.fedirchyk.blackjack.exceptions;

public class WalletNotFoundException extends GeneralGameException {

    private static final long serialVersionUID = 3174601337781181754L;

    public WalletNotFoundException() {
        super();
    }

    public WalletNotFoundException(String message) {
        super(message);
    }
}
