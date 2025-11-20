package com.gamestore.gamestore.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.dto.AddGameCaptureDTO;
import com.gamestore.gamestore.entity.GameCapture;
import com.gamestore.gamestore.entity.GameCaptureID;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.GameCaptureRepository;

@Service
public class GameCaptureService {

    @Autowired
    private GameCaptureRepository gameCaptureRepo;

    public List<GameCapture> getGameCapture(Integer gameID){
        if(gameID<=0){
            throw new MainErrorException("Mã trò chơi không hợp lệ");
        }
        return gameCaptureRepo.findByGameID(gameID);
    }
    public Map<String,Object> addGameCapture(AddGameCaptureDTO addGameCaptureDTO){
        
        int i = 1;

        for (String s: addGameCaptureDTO.getUrls()) {
            GameCapture gameCapture = new GameCapture();
            gameCapture.setGameID(addGameCaptureDTO.getGameID());
            gameCapture.setUrl(s);
            gameCapture.setNumberOrder(i);
            gameCaptureRepo.save(gameCapture);
            ++i;
        }
        return Map.of(
            "status","thành công",
            "message","Thêm hình ảnh trò chơi thành công"
        );

    }

    public Map<String,Object> deleteGameCapture(GameCaptureID gameCaptureID){
        System.out.println("aaa:"+ gameCaptureID.getGameID());
        GameCapture gameCapture = gameCaptureRepo.findById(gameCaptureID).orElseThrow(()-> new MainErrorException("Không tìm thấy mã Game Capture"));
        gameCaptureRepo.delete(gameCapture);

        return Map.of(
            "status","thành công",
            "message","Xoá hình ảnh trò chơi thành công"
        );
    }
}
