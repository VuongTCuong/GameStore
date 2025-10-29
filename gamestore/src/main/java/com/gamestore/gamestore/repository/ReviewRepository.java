package com.gamestore.gamestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @NativeQuery("select reviewID, reviewContent, reviewDate, userID, gameID, " + 
                 "case when userID = ?1 then 1 else 0 end as temp_num " + 
                 "from review " +
                 "where gameID=?2 " +
                 "order by temp_num desc ")
    List<Review> findAll_byGameandUser(Integer userID, Integer gameID);
}

