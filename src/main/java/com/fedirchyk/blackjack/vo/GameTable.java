package com.fedirchyk.blackjack.vo;

import com.fedirchyk.blackjack.entity.Wallet;

public class GameTable {
    
    private Wallet wallet;

    private String gameStatus;

    private int dealerCoins;

    private int playerCoins;

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
}
