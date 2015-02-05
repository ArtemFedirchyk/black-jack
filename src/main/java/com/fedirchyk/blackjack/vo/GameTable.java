package com.fedirchyk.blackjack.vo;

import java.io.Serializable;
import java.util.Stack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fedirchyk.blackjack.entity.Wallet;

/**
 * This is general VO, which contains all common information about Game process and Player's wallet(including balance)
 * 
 * @author artem.fedirchyk
 * 
 */
public class GameTable implements Serializable {

    private static final long serialVersionUID = -3428335022552376416L;

    private Wallet wallet;

    private String gameStatus;

    private int dealerCoins;

    private int playerCoins;

    @JsonIgnore
    private Card dealerHiddenCard;

    @JsonIgnore
    private Stack<Card> cardDeck = new Stack<Card>();

    private Stack<Card> playerCards = new Stack<Card>();

    private Stack<Card> dealerCards = new Stack<Card>();

    public GameTable(Wallet wallet) {
        this.wallet = wallet;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getDealerCoins() {
        return dealerCoins;
    }

    public void setDealerCoins(int dealerCoins) {
        this.dealerCoins = dealerCoins;
    }

    public int getPlayerCoins() {
        return playerCoins;
    }

    public void setPlayerCoins(int playerCoins) {
        this.playerCoins = playerCoins;
    }

    public Card getDealerHiddenCard() {
        return dealerHiddenCard;
    }

    public void setDealerHiddenCard(Card dealerHiddenCard) {
        this.dealerHiddenCard = dealerHiddenCard;
    }

    public Stack<Card> getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(Stack<Card> cardDeck) {
        this.cardDeck = cardDeck;
    }

    public Stack<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Stack<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public Stack<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(Stack<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((wallet == null) ? 0 : wallet.hashCode());
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
        GameTable other = (GameTable) obj;
        if (wallet == null) {
            if (other.wallet != null)
                return false;
        } else if (!wallet.equals(other.wallet))
            return false;
        return true;
    }
}
