package com.fedirchyk.blackjack.exceptions;

public class GeneralGameException extends RuntimeException{

    private static final long serialVersionUID = -8162898552680343552L;

    public GeneralGameException(){
        super();
    }
    
    public GeneralGameException(String message){
        super(message);
    }
}
