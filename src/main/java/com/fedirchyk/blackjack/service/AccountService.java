package com.fedirchyk.blackjack.service;

import com.fedirchyk.blackjack.vo.GameTable;

/**
 * Contains all logic associated with Player
 * 
 * @author artem.fedirchyk
 * 
 */
public interface AccountService {

    GameTable initializePlayer(double balance);

}
