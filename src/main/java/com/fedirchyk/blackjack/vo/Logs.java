package com.fedirchyk.blackjack.vo;

import java.io.Serializable;
import java.util.List;

import com.fedirchyk.blackjack.entity.Logging;

/**
 * This is VO, which contains information about Logs
 * 
 * @author artem.fedirchyk
 * 
 */
public class Logs implements Serializable {

    private static final long serialVersionUID = 1561177790583806596L;

    private String logType;

    private List<Logging> logs;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public List<Logging> getLogs() {
        return logs;
    }

    public void setLogs(List<Logging> logs) {
        this.logs = logs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((logType == null) ? 0 : logType.hashCode());
        result = prime * result + ((logs == null) ? 0 : logs.hashCode());
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
        Logs other = (Logs) obj;
        if (logType == null) {
            if (other.logType != null)
                return false;
        } else if (!logType.equals(other.logType))
            return false;
        if (logs == null) {
            if (other.logs != null)
                return false;
        } else if (!logs.equals(other.logs))
            return false;
        return true;
    }
}
