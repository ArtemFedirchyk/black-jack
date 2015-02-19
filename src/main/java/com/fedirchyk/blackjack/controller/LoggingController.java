package com.fedirchyk.blackjack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.service.LogsService;

@RestController("/")
public class LoggingController {

    @Autowired
    private LogsService logsService;
    
    @RequestMapping(value="logs/{gameId}", method = RequestMethod.GET)
    public List<Logging> getNidedLogs(@PathVariable int gameId){
        return logsService.getAllLogsForSpecifiedGame(gameId); 
    }
    
}
