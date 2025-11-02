package com.gamestore.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamestore.gamestore.entity.Game;
import java.util.List;


public interface GameRepository extends JpaRepository<Game, Integer>{
    boolean existsByGameID(Integer gameID);

    List<Game> findByGameNameContaining(String gameName);   
    List<Game> findByPriceBetween(float minPrice, float maxPrice);
    List<Game> findByGameNameContainingAndPriceBetween(String gameName, float minPrice, float maxPrice);
}
