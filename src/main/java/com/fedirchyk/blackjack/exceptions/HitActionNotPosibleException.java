package com.fedirchyk.blackjack.exceptions;

public class HitActionNotPosibleException extends GeneralGameException {

    private static final long serialVersionUID = 4588994116404457925L;

    public HitActionNotPosibleException() {
        super();
    }

    public HitActionNotPosibleException(String message) {
        super(message);
    }

}
