package com.gamestore.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.CartDetail;
import com.gamestore.gamestore.entity.CartDetailID;

import java.util.List;
import java.util.Optional;




@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailID> {
    
    List<CartDetail> findByCartID(Integer cartID);
    Optional<CartDetail> findByCartIDAndGameID(Integer cartID, Integer gameID);
    void deleteByCartIDAndGameID(Integer cartID, Integer gameID);
}