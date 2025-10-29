package com.gamestore.gamestore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.dto.CreateReviewDTO;
import com.gamestore.gamestore.entity.Review;
import com.gamestore.gamestore.entity.User;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.GameRepository;
import com.gamestore.gamestore.repository.ReviewRepository;
import com.gamestore.gamestore.repository.UserRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private UserRepository userRepo;

    // lấy tất cả review của 1 game - review của người dùng hiện tại được đưa lên đầu
    public Map<String,Object> getReviewByGame(UserDetails userDetails, Integer gameID){
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()->new MainErrorException("Không tìm thấy người dùng muốn review"));

        List<Review> reviews = reviewRepo.findAll_byGameandUser(user.getUserID(), gameID);
        return Map.of(
            "userID",user.getUserID(),
            "result",reviews
        );
    }

    // tạo review 1 game 
    public Map<String,Object> addReview(UserDetails userDetails, CreateReviewDTO createReviewDTO, Integer gameID){
        if(!gameRepo.existsByGameID(gameID)){
            throw new MainErrorException("Không tìm thấy trò chơi muốn review");
        }
      
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()->new MainErrorException("Không tìm thấy người dùng muốn review"));
        Review review = new Review();
        review.setReviewContent(createReviewDTO.getReviewContent());
        review.setReviewDate(LocalDate.now());
        review.setUserID(user.getUserID());
        review.setGameID(gameID);
        reviewRepo.save(review);
        return Map.of(
            "status","thành công",
            "message","tạo review thành công",
            "result",review
        );
    }

    // cập nhật 1 review
    public Map<String,Object> updateReview(Integer reviewID, CreateReviewDTO createReviewDTO){
        Review review = reviewRepo.findById(reviewID).orElseThrow(()-> new MainErrorException("Không tìm thấy mã review"));
  
        review.setReviewContent(createReviewDTO.getReviewContent());
        review.setReviewDate(LocalDate.now());
        reviewRepo.save(review);
        return Map.of(
            "status","thành công",
            "message","cập nhật review thành công",
            "result",review
        );
    }

    // xoá 1 review
    // customer - xoá của bản thân, admin - xoá của bất kì customer
    public Map<String,Object> deleteReview(UserDetails userDetails, Integer reviewID){
        String authority = userDetails.getAuthorities().iterator().next().toString();
        Review review = reviewRepo.findById(reviewID).orElseThrow(()-> new MainErrorException("Không tìm thấy mã review"));
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));
       
        if(user.getUserID()!=review.getUserID() && authority.equals("ROLE_CUSTOMER")){
            throw new MainErrorException("Bạn không có quyền xoá review này");
        }

        reviewRepo.delete(review);
        return Map.of(
            "status","thành công",
            "message","xoá review thành công"
        );
    }
}
