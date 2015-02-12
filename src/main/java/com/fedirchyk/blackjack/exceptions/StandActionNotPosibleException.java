package com.fedirchyk.blackjack.exceptions;

public class StandActionNotPosibleException extends GeneralGameException {

    private static final long serialVersionUID = 7005985695508034061L;

    public StandActionNotPosibleException() {
        super();
    }

    public StandActionNotPosibleException(String message) {
        super(message);
    }
}
