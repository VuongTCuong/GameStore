package com.gamestore.gamestore.controller;


import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.dto.CreateReviewDTO;
import com.gamestore.gamestore.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/all/{gameID}")
    public ResponseEntity<Map<String,Object>> getReviewByGame(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer gameID){
        return ResponseEntity.ok().body(reviewService.getReviewByGame(userDetails, gameID));
    }

    @PostMapping("/add/{gameID}")
    public ResponseEntity<Map<String,Object>> addReview(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody CreateReviewDTO createReviewDTO, @PathVariable Integer gameID){
        return ResponseEntity.ok().body(reviewService.addReview(userDetails, createReviewDTO, gameID));
    }

    @PutMapping("/update/{reviewID}")
    public ResponseEntity<Map<String,Object>> updateReview(@PathVariable Integer reviewID, @Valid @RequestBody CreateReviewDTO createReviewDTO){
        return ResponseEntity.ok().body(reviewService.updateReview(reviewID, createReviewDTO));
    }
    
    @DeleteMapping("/delete/{reviewID}")
    public ResponseEntity<Map<String,Object>> deleteReview(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer reviewID){
        return ResponseEntity.ok().body(reviewService.deleteReview(userDetails, reviewID));
    }
}
