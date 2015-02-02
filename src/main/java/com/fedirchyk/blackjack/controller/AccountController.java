package com.fedirchyk.blackjack.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fedirchyk.blackjack.exceptions.ExceptionInformation;

@RestController
@RequestMapping(value = "/wallet", produces = "application/json")
public class AccountController {

    private static Logger logger = Logger.getLogger(AccountController.class);

    private static final double DEFAULT_WALLET_BALANCE = 100;

    @RequestMapping(value = "/initPlayer/{initBalane}", method = RequestMethod.GET)
    public void initializePlayer(@PathVariable double initBalance) {
        logger.info("Started the process of player's initialisation");
    }

    @RequestMapping(value = "/addMoney/{walletId}/{money}", method = RequestMethod.GET)
    public void increaseBalanse(@PathVariable int walletId, @PathVariable double money) {
        logger.info("");
    }

    @ExceptionHandler(value = RuntimeException.class)
    private ExceptionInformation handleBadRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionInformation(request.getRequestURL().toString(), exception);
    }
}
