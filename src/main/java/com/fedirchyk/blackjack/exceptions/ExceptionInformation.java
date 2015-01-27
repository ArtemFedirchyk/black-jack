package com.fedirchyk.blackjack.exceptions;

public class ExceptionInformation {

    public final String url;

    public final String exception;

    public ExceptionInformation(String url, Exception exception) {
        this.url = url;
        this.exception = exception.getLocalizedMessage();
    }

}
