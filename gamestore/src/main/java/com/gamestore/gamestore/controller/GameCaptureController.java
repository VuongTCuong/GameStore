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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.dto.AddGameCaptureDTO;
import com.gamestore.gamestore.entity.GameCapture;
import com.gamestore.gamestore.entity.GameCaptureID;
import com.gamestore.gamestore.service.GameCaptureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/game-capture")
public class GameCaptureController {

    @Autowired
    private GameCaptureService gameCaptureService;

    @GetMapping("/get/{gameID}")
    public ResponseEntity<List<GameCapture>> getGameCapture(@PathVariable Integer gameID){
        return ResponseEntity.ok().body(gameCaptureService.getGameCapture(gameID));
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> addGameCapture(@Valid @RequestBody AddGameCaptureDTO addGameCaptureDTO){
        return ResponseEntity.ok().body(gameCaptureService.addGameCapture(addGameCaptureDTO));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> addGameCapture(@RequestBody GameCaptureID gameCaptureID){
        return ResponseEntity.ok().body(gameCaptureService.deleteGameCapture(gameCaptureID));
    }


}
