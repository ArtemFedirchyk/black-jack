package com.fedirchyk.blackjack.exceptions;

public class WalletBalanceNotEnoughException extends GeneralGameException {

    private static final long serialVersionUID = 8791107315646222895L;

    public WalletBalanceNotEnoughException() {
        super();
    }

    public WalletBalanceNotEnoughException(String message) {
        super(message);
    }
}
