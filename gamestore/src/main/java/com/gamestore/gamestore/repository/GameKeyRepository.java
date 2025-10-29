package com.gamestore.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.gamestore.gamestore.entity.GameKey;

import java.util.List;
;


public interface GameKeyRepository extends JpaRepository<GameKey, Integer> {

    boolean existsByKeyValue(String keyValue);
    List<GameKey> findAllByGameID(Integer gameID);
    GameKey findByKeyValue(String keyValue);

    boolean existsByKeyValueAndKeyIDNot(String keyValue, Integer keyID);
}
