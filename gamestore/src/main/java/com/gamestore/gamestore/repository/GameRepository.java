package com.gamestore.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamestore.gamestore.entity.Game;

public interface GameRepository extends JpaRepository<Game, Integer>{
    boolean existsByGameID(Integer gameID);
}
