package com.fedirchyk.blackjack.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fedirchyk.blackjack.exceptions.ExceptionInformation;
import com.fedirchyk.blackjack.exceptions.WalletBalanceNotEnoughException;
import com.fedirchyk.blackjack.exceptions.WalletNotFoundException;
import com.fedirchyk.blackjack.exceptions.constatnts.ExceptionConstants;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.service.GameService;
import com.fedirchyk.blackjack.vo.GameTable;

/**
 * This controller is responsible for processing all requests which are associated with Game process
 * 
 * @author artem.fedirchyk
 * 
 */
@RestController
@RequestMapping(value = "/game/{walletId}", produces = "application/json")
public class GameController {

    private static Logger logger = Logger.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private AccountService accountService;

    /**
     * Starts the Game process in next way - Player makes bet and game starts without asking about 'Deal' action
     * 
     * @param bet
     *            - value of bet, which Player made
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    @RequestMapping(value = "/start/{bet}", method = RequestMethod.GET)
    public GameTable startGame(@PathVariable int walletId, @PathVariable int bet) {
        if (accountService.isWalletExist(walletId)) {
            if (accountService.isPlayerBalanceEnough(walletId, bet)) {
                logger.info("Started game with bet - " + bet);
                gameService.makeBet(walletId, bet);
                return gameService.dealAction(walletId);
            }
            throw new WalletBalanceNotEnoughException(ExceptionConstants.WALLET_BALANCE_NOT_ENOUGH);
        }
        throw new WalletNotFoundException(ExceptionConstants.WALLET_NOT_FOUND);
    }

    /**
     * Starts the process of making Bet by Player
     * 
     * @param bet
     *            - value of bet, which Player made
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    @RequestMapping(value = "/bet/{bet}", method = RequestMethod.GET)
    public GameTable makeBet(@PathVariable int walletId, @PathVariable int bet) {
        if (accountService.isWalletExist(walletId)) {
            if (accountService.isPlayerBalanceEnough(walletId, bet)) {
                logger.info("Started process of making Bet with count of coins - " + bet);
                return gameService.makeBet(walletId, bet);
            }
            throw new WalletBalanceNotEnoughException(ExceptionConstants.WALLET_BALANCE_NOT_ENOUGH);
        }
        throw new WalletNotFoundException(ExceptionConstants.WALLET_NOT_FOUND);

    }

    /**
     * Starts the Game process in next way - first Player makes bet then he confirms 'Deal' action and only after that
     * Game starts
     * 
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    @RequestMapping(value = "/deal", method = RequestMethod.GET)
    public GameTable makeDeal(@PathVariable int walletId) {
        if (accountService.isWalletExist(walletId)) {
            logger.info("Strated process when Player make DEAL action");
            return gameService.dealAction(walletId);
        }
        throw new WalletNotFoundException(ExceptionConstants.WALLET_NOT_FOUND);
    }

    /**
     * Starts the process of performing Player's 'Hit' action
     * 
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    @RequestMapping(value = "/hit", method = RequestMethod.GET)
    public GameTable hit(@PathVariable int walletId) {
        if (accountService.isWalletExist(walletId)) {
            logger.info("Started process when Player makes HIT action");
            return gameService.hitAction(walletId);
        }
        throw new WalletNotFoundException(ExceptionConstants.WALLET_NOT_FOUND);
    }

    /**
     * Starts the process of performing Player's 'Stand' action and after that performing main logic for Dealer's game
     * 
     * @return Object of {@link GameTable} type, which contains all information about Player's wallet and Game state
     */
    @RequestMapping(value = "/stand", method = RequestMethod.GET)
    public GameTable stand(@PathVariable int walletId) {
        if (accountService.isWalletExist(walletId)) {
            logger.info("Started process when Player makes STAND action");
            return gameService.standAction(walletId);
        }
        throw new WalletNotFoundException(ExceptionConstants.WALLET_NOT_FOUND);
    }

    @ExceptionHandler(value = RuntimeException.class)
    private ExceptionInformation handleBadRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionInformation(request.getRequestURL().toString(), exception);
    }
}
