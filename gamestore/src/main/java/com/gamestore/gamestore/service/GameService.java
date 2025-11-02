package com.gamestore.gamestore.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.gamestore.gamestore.dto.CreateGameDTO;
import com.gamestore.gamestore.dto.CreateGameKeyDTO;
import com.gamestore.gamestore.dto.UpdateGameKeyDTO;
import com.gamestore.gamestore.entity.Game;
import com.gamestore.gamestore.entity.GameKey;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.GameKeyRepository;
import com.gamestore.gamestore.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private GameKeyRepository gameKeyRepo;
    // Thêm trò chơi - của admin
    public Map<String,Object> addGame(CreateGameDTO createGameDTO){
        Game game = new Game();

        game.setGameName(createGameDTO.getGameName());
        game.setPoster(createGameDTO.getPoster());
        game.setGenre(createGameDTO.getGenre());
        game.setGameDescription(createGameDTO.getGameDescription());
        game.setPlatform(createGameDTO.getPlatform());
        game.setPublisher(createGameDTO.getPublisher());
        game.setReleaseDate(createGameDTO.getReleaseDate());
        game.setStockQuantity(createGameDTO.getStockQuantity());
        game.setPrice(createGameDTO.getPrice());
        gameRepo.save(game);
        return Map.of(
            "status","thành công",
            "message","tạo trò chơi thành công",
            "result",game
        );
    }

    // Thêm key trò chơi - của admin
    public Map<String,Object> addKeyGame(CreateGameKeyDTO createKeyGameDTO){
        if(gameKeyRepo.existsByKeyValue(createKeyGameDTO.getKeyValue())){
            throw new MainErrorException("Key trò chơi không được trùng");
        }
        if(!gameRepo.existsByGameID(createKeyGameDTO.getGameID())){
            throw new MainErrorException("Không tìm thấy mã trò chơi");
        }
        GameKey gameKey = new GameKey();
        gameKey.setKeyValue(createKeyGameDTO.getKeyValue());
        gameKey.setStatus(createKeyGameDTO.getStatus());
        gameKey.setGameID(createKeyGameDTO.getGameID());

        gameKeyRepo.save(gameKey);
        
        return Map.of(
            "status","thành công",
            "message","Thêm Key game thành công",
            "result",gameKey
        );
    }

    // Lấy tất cả trò chơi
    public List<Game> getAllGame(){
        List<Game> games = gameRepo.findAll();
        return games;

    }
    // Lấy thông tin trò chơi cụ thể 
    public Map<String,Object> getGame(Integer gameID){
        Game game = gameRepo.findById(gameID).orElseThrow(()-> new MainErrorException("Không tìm thấy trò chơi cụ thể"));
        return Map.of(
            "status","thành công",
            "message","Tìm thành công",
            "result",game
        );
    }

    // Lấy tất cả key của trò chơi cụ thể 
    public List<GameKey> getGameKey(Integer gameID){
        List<GameKey> gamekeys = gameKeyRepo.findAllByGameID(gameID);       
        return gamekeys;
    }

    // Cập nhật trò chơi - của admin
    public Map<String,Object> updateGame(CreateGameDTO createGameDTO, Integer gameID){ //sử dụng CreateGameDTO vì có trường dữ liệu cùng với lúc update
        Game game = gameRepo.findById(gameID).orElseThrow(()-> new MainErrorException("Không tìm thấy trò chơi"));
        game.setGameName(createGameDTO.getGameName());
        game.setPoster(createGameDTO.getPoster());
        game.setGenre(createGameDTO.getGenre());
        game.setGameDescription(createGameDTO.getGameDescription());
        game.setPlatform(createGameDTO.getPlatform());
        game.setPublisher(createGameDTO.getPublisher());
        game.setReleaseDate(createGameDTO.getReleaseDate());
        game.setStockQuantity(createGameDTO.getStockQuantity());
        game.setPrice(createGameDTO.getPrice());

        gameRepo.save(game);
        return Map.of(
            "status","thành công",
            "message","Tìm thành công",
            "result",game
        );
    }

    // Cập nhật key của game cụ thể - của admin
    public Map<String,Object> updateGameKey(UpdateGameKeyDTO updateGameKeyDTO, Integer keyID){
        GameKey gameKey = gameKeyRepo.findById(keyID).orElseThrow(()-> new MainErrorException("Không tìm thấy key của trò chơi"));
        
        if(gameKeyRepo.existsByKeyValueAndKeyIDNot(updateGameKeyDTO.getKeyValue(), keyID)){
            throw new MainErrorException("Không thể cập nhật trùng key");
        }
        gameKey.setKeyValue(updateGameKeyDTO.getKeyValue());
        gameKey.setStatus(updateGameKeyDTO.getStatus());
        gameKeyRepo.save(gameKey);
        return Map.of(
            "status","thành công",
            "message","Thêm key của trò chơi thành công",
            "result",gameKey
        );
    }

    // Xoá 1 trò chơi cụ thể - của admin
    public Map<String,Object> deleteGame(Integer gameID){
        if(!gameRepo.existsById(gameID)){
            throw new MainErrorException("Không thể tìm thấy trò chơi");
        }
        gameRepo.deleteById(gameID);
        return Map.of(
            "status","thành công",
            "message","Xoá trò chơi thành công"
        );
    }
    
    // Xoá 1 key của trò chơi cụ thể - của admin
    public Map<String,Object> deleteGameKey(Integer keyID){
        if(!gameKeyRepo.existsById(keyID)){
            throw new MainErrorException("Không thể tìm thấy key của trò chơi");
        }
        gameKeyRepo.deleteById(keyID);
        return Map.of(
            "status","thành công",
            "message","Xoá key của trò chơi thành công"
        );
    }

    public List<Game> filterByGameNameAndPrice(String gameName, float minPrice, float maxPrice){
        
        if(minPrice<0 || maxPrice<0 || minPrice>maxPrice){
            throw new MainErrorException("Khoảng giá tiền không hợp lệ");
        }

        if(gameName.isBlank()){
            return gameRepo.findByPriceBetween(minPrice, maxPrice);
        }
        
        return gameRepo.findByGameNameContainingAndPriceBetween(gameName, minPrice, maxPrice);

    }

 
}
