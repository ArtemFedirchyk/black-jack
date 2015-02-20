package com.fedirchyk.blackjack.vo.enumerations;

/**
 * Contains values of Logs types
 * 
 * @author artem.fedirchyk
 * 
 */
public enum LogsType {

    LOGS_OF_GAME_TYPE("Logs of Game type"), LOGS_OFACCOUNT_TYPE("Logs of Account type");

    private String logType;

    private LogsType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return logType;
    }
}
