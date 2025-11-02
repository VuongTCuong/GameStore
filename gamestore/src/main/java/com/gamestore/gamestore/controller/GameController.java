package com.gamestore.gamestore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.dto.CreateGameDTO;
import com.gamestore.gamestore.dto.CreateGameKeyDTO;
import com.gamestore.gamestore.dto.UpdateGameKeyDTO;
import com.gamestore.gamestore.entity.Game;
import com.gamestore.gamestore.entity.GameKey;
import com.gamestore.gamestore.service.GameService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> addGame(@Valid @RequestBody CreateGameDTO createGameDTO){
        return ResponseEntity.ok().body(gameService.addGame(createGameDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_key")
    public ResponseEntity<Map<String,Object>> addKeyGame(@Valid @RequestBody CreateGameKeyDTO createKeyGameDTO){
        return ResponseEntity.ok().body(gameService.addKeyGame(createKeyGameDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGame(){
        return ResponseEntity.ok().body(gameService.getAllGame());
    }

    @GetMapping("/{gameID}")
    public ResponseEntity<Map<String,Object>> getGame(@PathVariable Integer gameID){
        return ResponseEntity.ok().body(gameService.getGame(gameID));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("key/{gameID}")
    public ResponseEntity<List<GameKey>> getGameKey(@PathVariable Integer gameID){
        return ResponseEntity.ok().body(gameService.getGameKey(gameID));
    }

    @PutMapping("/update/{gameID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> updateGame(@Valid @RequestBody CreateGameDTO createGameDTO, @PathVariable Integer gameID){    
        return ResponseEntity.ok().body(gameService.updateGame(createGameDTO,gameID));
    }

    @PutMapping("/key/update/{keyID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> updateGameKey(@Valid @RequestBody UpdateGameKeyDTO updateGameKeyDTO, @PathVariable Integer keyID){
        return ResponseEntity.ok().body(gameService.updateGameKey(updateGameKeyDTO,keyID));
    }
    

    @DeleteMapping("/delete/{gameID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> deleteGame(@PathVariable Integer gameID){
        return ResponseEntity.ok().body(gameService.deleteGame(gameID));
    }

    @DeleteMapping("/key/delete/{keyID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> deleteGameKey(@PathVariable Integer keyID){
        return ResponseEntity.ok().body(gameService.deleteGameKey(keyID));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Game>> filterByGameNameAndPrice(@RequestParam(required = false) String gameName, 
                                                               @RequestParam(required = false) float minPrice, 
                                                               @RequestParam(required = false) float maxPrice){
        return ResponseEntity.ok().body(gameService.filterByGameNameAndPrice(gameName, minPrice, maxPrice));
    }

}
