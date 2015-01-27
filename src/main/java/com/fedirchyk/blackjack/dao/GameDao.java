package com.fedirchyk.blackjack.dao;

import org.springframework.data.repository.CrudRepository;

import com.fedirchyk.blackjack.entity.Game;

public interface GameDao extends CrudRepository<Game, Integer> {

}
