package com.fedirchyk.blackjack.vo;

import java.io.Serializable;

import com.fedirchyk.blackjack.vo.enumerations.Rank;
import com.fedirchyk.blackjack.vo.enumerations.Suit;

/**
 * This is VO, which contains information about Card
 * 
 * @author artem.fedirchyk
 * 
 */
public class Card implements Serializable {

    private static final long serialVersionUID = -8806394137237506L;

    private Rank rank;

    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (rank != other.rank)
            return false;
        if (suit != other.suit)
            return false;
        return true;
    }
}
