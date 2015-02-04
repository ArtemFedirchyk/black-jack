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
import com.fedirchyk.blackjack.exceptions.WalletNotFoundException;
import com.fedirchyk.blackjack.exceptions.constatnts.ExceptionConstants;
import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.vo.GameTable;

/**
 * This controller is responsible for processing all requests which are associated with Player and Player's wallet
 * 
 * @author artem.fedirchyk
 */
@RestController
@RequestMapping(value = "/", produces = "application/json")
public class AccountController {

    private static Logger logger = Logger.getLogger(AccountController.class);

    private static final double DEFAULT_WALLET_BALANCE = 100;

    @Autowired
    private AccountService accountService;

    private boolean isPresent;

    /**
     * Initializes Player with default balance of Player's wallet
     * 
     * @return Object of {@link GameTable} type, which contains all information about Player and Game state
     */
    @RequestMapping(value = "/initPlayer", method = RequestMethod.GET)
    public GameTable initializePlayerWithDefaultBalance() {
        logger.info("Started the process of player's initialisation with default balance");
        return accountService.initializePlayer(DEFAULT_WALLET_BALANCE);
    }

    /**
     * Initializes Player with balance of Player's wallet from request
     * 
     * @param initBalance
     *            - value of initial balance of Player's wallet
     * @return Object of {@link GameTable} type, which contains all information about Player and Game state
     */
    @RequestMapping(value = "/initPlayerBalance/{initBalane}", method = RequestMethod.GET)
    public GameTable initializePlayerWithInputedBalance(@PathVariable double initBalance) {
        logger.info("Started the process of player's initialisation with inputed balance");
        return null;
    }

    /**
     * Adds some count of money for Player's wallet
     * 
     * @param walletId
     *            - value of Player's wallet, which should be registered in DB
     * @param money
     *            - value of incoming money for increasing Player's balance from request
     * @return Object of {@link GameTable} type, which contains all information about Player and Game state
     */
    @RequestMapping(value = "/addMoney/{walletId}/{money}", method = RequestMethod.GET)
    public GameTable increaseBalanse(@PathVariable int walletId, @PathVariable double money) {
        if (isPresent) {
            logger.info("Started the process of increasing the player's balance");
            return null;
        }
        throw new WalletNotFoundException(ExceptionConstants.WALLET_NOT_FOUND_EXCEPTION);
    }

    @ExceptionHandler(value = RuntimeException.class)
    private ExceptionInformation handleBadRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionInformation(request.getRequestURL().toString(), exception);
    }
}
