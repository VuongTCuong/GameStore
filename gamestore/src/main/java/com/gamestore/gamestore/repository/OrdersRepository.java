package com.gamestore.gamestore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findByUserID(Integer userID);
    boolean existsByUserID(Integer userID);
}
