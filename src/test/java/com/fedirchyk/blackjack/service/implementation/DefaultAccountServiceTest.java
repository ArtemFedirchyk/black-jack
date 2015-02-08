package com.fedirchyk.blackjack.service.implementation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fedirchyk.blackjack.service.AccountService;
import com.fedirchyk.blackjack.vo.GameTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@Transactional
public class DefaultAccountServiceTest {

    private static final double DEFAULT_BALANCE = 100;

    @Autowired
    private AccountService accountService;

    @Test
    public void testIsWalletExist() {
        GameTable gameTable = accountService.initializePlayer(DEFAULT_BALANCE);
        boolean isWalletExist = accountService.isWalletExist(gameTable.getWallet().getWalletId());
        Assert.assertTrue(isWalletExist);
    }

}
