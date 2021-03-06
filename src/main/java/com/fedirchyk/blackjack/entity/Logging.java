package com.fedirchyk.blackjack.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "logging")
public class Logging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id", nullable = false)
    private int logId;

    @Column(name = "game_status")
    private String gameStatus;

    @Column(name = "operation")
    private String operation;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "time")
    private String time;

    @Column(name = "playing_side")
    private String playingSide;

    @Column(name = "common_scores_count")
    private int commonScoresCount;

    @Column(name = "card_scores_value")
    private int cardScoresValue;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlayingSide() {
        return playingSide;
    }

    public void setPlayingSide(String playingSide) {
        this.playingSide = playingSide;
    }

    public int getCommonScoresCount() {
        return commonScoresCount;
    }

    public void setCommonScoresCount(int commonScoresCount) {
        this.commonScoresCount = commonScoresCount;
    }

    public int getCardScoresValue() {
        return cardScoresValue;
    }

    public void setCardScoresValue(int cardScoresValue) {
        this.cardScoresValue = cardScoresValue;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + logId;
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
        Logging other = (Logging) obj;
        if (logId != other.logId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Logging [logId=" + logId + ", gameStatus=" + gameStatus + ", operation=" + operation
                + ", operationType=" + operationType + ", time=" + time + ", playingSide=" + playingSide
                + ", commonScoresCount=" + commonScoresCount + ", cardScoresValue=" + cardScoresValue + ", gameID="
                + game.getGameId() + "]";
    }
}
