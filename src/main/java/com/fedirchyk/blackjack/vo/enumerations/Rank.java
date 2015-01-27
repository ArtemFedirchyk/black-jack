package com.fedirchyk.blackjack.vo.enumerations;

public enum Rank {
    
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),JACK(10), QUEEN(10), KING(10), ACE(11);
    
    private int cardValue;
    
    Rank(int cardValue){
        this.cardValue = cardValue;
    }

    public int getCardValue() {
        return cardValue;
    }
}
