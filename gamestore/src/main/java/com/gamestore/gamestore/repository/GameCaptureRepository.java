package com.gamestore.gamestore.repository;

import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.GameCapture;
import com.gamestore.gamestore.entity.GameCaptureID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


@Repository
public interface GameCaptureRepository extends JpaRepository<GameCapture, GameCaptureID> {

    List<GameCapture> findByGameID(Integer gameID);
}
