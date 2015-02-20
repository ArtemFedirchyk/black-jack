package com.fedirchyk.blackjack.controller;

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
import com.fedirchyk.blackjack.vo.Logs;

/**
 * This controller is responsible for processing all requests which are associated with Logging process
 * 
 * @author artem.fedirchyk
 * 
 */
@RestController("/")
public class LoggingController {

    @Autowired
    private LogsService logsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GameService gameService;

    /**
     * Starts the process of searching needed logs for some specified Game, it means that found logs will contain about
     * all Games' actions, which were made during this specified Game
     * 
     * @param gameId
     *            - value of Game ID, which will be used for searching needed logs in DB for specified Game
     * @return list of objects of {@link Logging} type, which contains logged information about specified Game
     */
    @RequestMapping(value = "gameLogs/{gameId}", method = RequestMethod.GET)
    public Logs getNeededGameLogs(@PathVariable int gameId) {
        if (gameService.isGameExist(gameId)) {
            return logsService.getAllLogsForSpecifiedGame(gameId);
        }
        throw new GameNotFoundException(ExceptionConstants.GAME_NOT_FOUND);
    }

    /**
     * Starts the process of searching needed logs for some specified Player, it means that found logs will contain all
     * information about Acccount's and Game's actions which were made by this Player. Also, this logs will contain
     * information about all Games, which were played by Player
     * 
     * @param walletId
     *            - value of Player's Wallet ID, which will be used for searching needed logs
     * @return list of objects of {@link Logging} type with information about all actions(Game and Account type), which
     *         were made by specified Player
     */
    @RequestMapping(value = "playerLogs/{walletId}", method = RequestMethod.GET)
    public Logs getNeededAccountLogs(@PathVariable int walletId) {
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
