package com.fedirchyk.blackjack.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.exceptions.ExceptionInformation;
import com.fedirchyk.blackjack.exceptions.GeneralGameException;
import com.fedirchyk.blackjack.service.GameService;

@RestController
@RequestMapping(value = "/test", produces = "application/json")
public class GameController {

    private static Logger logger = Logger.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/wallet/{walletId}", method = RequestMethod.GET, produces = "application/json")
    public Wallet takeWallet(@PathVariable Integer walletId) {
        if(gameService.isWalletExist(walletId)){
            return gameService.getWallet(walletId);
        }
        throw new GeneralGameException("ALARM!!! :) WALLET does not exist");
    }

    @RequestMapping(value = "/{text}")
    public void saveWallet(@PathVariable String text) {
        logger.info("saveWallet()");
        Wallet wallet = new Wallet();
        wallet.setOperation(text);
        gameService.saveWallet(wallet);
    }

    @ExceptionHandler(value = RuntimeException.class)
    private ExceptionInformation handleBarRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionInformation(request.getRequestURL().toString(), exception);
    }
}
