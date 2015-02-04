package com.fedirchyk.blackjack.service;

import com.fedirchyk.blackjack.vo.GameTable;

public interface AccountService {
    GameTable initializePlayer(double balance);
}
