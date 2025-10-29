package com.gamestore.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.Cart;



@Repository
public interface CartRepository extends JpaRepository<Cart, Integer > {
    Cart findByUserID(Integer userID);
}
