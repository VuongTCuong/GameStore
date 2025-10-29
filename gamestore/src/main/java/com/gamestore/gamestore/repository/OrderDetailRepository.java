package com.gamestore.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.OrderDetail;
import com.gamestore.gamestore.entity.OrderDetailID;
import java.util.List;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailID>{

    List<OrderDetail> findByOrderID(Integer orderID);
}
