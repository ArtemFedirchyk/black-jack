package com.fedirchyk.blackjack.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fedirchyk.blackjack.exceptions.ExceptionInformation;
import com.fedirchyk.blackjack.vo.GameTable;

@RestController
@RequestMapping(value = "/game", produces = "application/json")
public class GameController {

    private static Logger logger = Logger.getLogger(GameController.class);

    @RequestMapping(value = "/start/{bet}", method = RequestMethod.GET)
    public GameTable startGame(@PathVariable double bet) {
        logger.info("Started game with bet - " + bet);
        return null;
    }

    @RequestMapping(value = "/bet/{count}", method = RequestMethod.GET)
    public GameTable makeBet(@PathVariable double count) {
        logger.info("Started process of making Bet with count of money - " + count);
        return null;
    }

    @RequestMapping(value = "/deal", method = RequestMethod.GET)
    public GameTable makeDeal() {
        logger.info("");
        return null;
    }

    @RequestMapping(value = "/hit", method = RequestMethod.POST)
    public GameTable hit() {
        logger.info("Started process when Player makes HIT action");
        return null;
    }

    @RequestMapping(value = "/stand", method = RequestMethod.GET)
    public GameTable stand() {
        logger.info("Started process when Player makes STAND action");
        return null;
    }

    @ExceptionHandler(value = RuntimeException.class)
    private ExceptionInformation handleBadRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionInformation(request.getRequestURL().toString(), exception);
    }

    // @Autowired
    // private GameService gameService;
    //
    // @RequestMapping(value = "/wallet/{walletId}", method = RequestMethod.GET, produces = "application/json")
    // public Wallet takeWallet(@PathVariable Integer walletId) {
    // if(gameService.isWalletExist(walletId)){
    // return gameService.getWallet(walletId);
    // }
    // throw new GeneralGameException("ALARM!!! :) WALLET does not exist");
    // }
    //
    // @RequestMapping(value = "/{text}")
    // public void saveWallet(@PathVariable String text) {
    // logger.info("saveWallet()");
    // Wallet wallet = new Wallet();
    // wallet.setOperation(text);
    // gameService.saveWallet(wallet);
    // }
    //
    // @ExceptionHandler(value = RuntimeException.class)
    // private ExceptionInformation handleBadRequest(HttpServletRequest request, Exception exception) {
    // return new ExceptionInformation(request.getRequestURL().toString(), exception);
    // }
}
