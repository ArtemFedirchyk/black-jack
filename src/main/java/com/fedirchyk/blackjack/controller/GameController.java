package com.fedirchyk.blackjack.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fedirchyk.blackjack.entity.Wallet;
import com.fedirchyk.blackjack.service.GameService;

@RestController
@RequestMapping(value = "/test", produces="application/json")
public class GameController {
    
    private static Logger logger= Logger.getLogger(GameController.class);
    
    @Autowired
    private GameService gameService;
    
    @RequestMapping(value="/wallet/{walletId}", method=RequestMethod.GET, produces = "application/json")
    public @ResponseBody Wallet takeWallet(@PathVariable int walletId){
        logger.info("takeWallet() with id - " + walletId);
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setOperation("Test Operation");
        
        return gameService.getWallet(wallet);
    }
}
