package com.gamestore.gamestore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.Orders;
import java.util.List;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    
    boolean existsByUserID(Integer userID);
    List<Orders> findByUserID(Integer userID);
}
