package com.fedirchyk.blackjack.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fedirchyk.blackjack.entity.Logging;
import com.fedirchyk.blackjack.exceptions.ExceptionInformation;
import com.fedirchyk.blackjack.exceptions.GameNotFoundException;
import com.fedirchyk.blackjack.exceptions.WalletNotFoundException;
import com.fedirchyk.blackjack.exceptions.constatnts.ExceptionConstants;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.service.GameService;
import com.fedirchyk.blackjack.service.LogsService;

@RestController("/")
public class LoggingController {

    @Autowired
    private LogsService logsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "gameLogs/{gameId}", method = RequestMethod.GET)
    public List<Logging> getNeededGameLogs(@PathVariable int gameId) {
        if (gameService.isGameExist(gameId)) {
            return logsService.getAllLogsForSpecifiedGame(gameId);
        }
        throw new GameNotFoundException(ExceptionConstants.GAME_NOT_FOUND);
    }

    @RequestMapping(value = "playerLogs/{walletId}", method = RequestMethod.GET)
    public List<Logging> getNeededAccountLogs(@PathVariable int walletId) {
        if (accountService.isWalletExist(walletId)) {
            return logsService.getAllLogsForSpecifiedPlayer(walletId);
        }
        throw new WalletNotFoundException(ExceptionConstants.WALLET_NOT_FOUND);
    }

    @ExceptionHandler(value = RuntimeException.class)
    private ExceptionInformation handleBadRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionInformation(request.getRequestURL().toString(), exception);
    }

}
