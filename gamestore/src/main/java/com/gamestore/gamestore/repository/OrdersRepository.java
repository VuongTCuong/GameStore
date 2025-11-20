package com.gamestore.gamestore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.entity.Orders;


import java.time.LocalDate;
import java.util.List;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    
    boolean existsByUserID(Integer userID);
    List<Orders> findByUserID(Integer userID);

    @NativeQuery("select count(orderID) as totalOrders, sum(totalAmount) as totalRevenue, count(distinct(userID)) as totalCustomers " +
                 "from orders")
    Object[] statisticOrders();

    @NativeQuery("select gameName, sum(quantity) as quantity, sum(quantity*priceAtPurchase) as totalRevenue "+
                    "from orderdetail, game "+
                    "where game.gameID = orderdetail.gameID "+
                    "group by orderdetail.gameID "+
                    "order by quantity desc "+
                    "limit 1")
    Object[] statisticGames();

    @NativeQuery("select orderID, user.fullName, totalAmount, orderDate "+
                "from orders, user "+
                "where user.userID=orders.userID "+
                "order by orderDate desc "+
                "limit 3")
    Object[] firstThreeOrders();

    @NativeQuery("select orderID, user.fullName, orderDate, totalAmount, orders.state "+
                "from orders, user "+
                "where user.userID=orders.userID  and orders.state='Đã giao' and orderDate between ?1 and ?2")
    Object[] soldOrders(LocalDate date1, LocalDate date2);

    @NativeQuery("select sum(totalAmount) as totalAmount, date_format(orderDate,'%m-%Y') as revenueMonth "+
                "from orders "+
                "where orderDate between ?1 and ?2 "+
                "group by revenueMonth "+
                "order by MIN(orderDate) asc")
    Object[] statisticbyMonth(LocalDate startDate, LocalDate endDate);

    @NativeQuery("select user.userID, fullName, email, count(orderID) as totalOrders "+
                "from user "+
                "join orders on orders.userID = user.userID "+
                "group by userID")
    Object[] statistictotalOrderbyUser();
    
    @NativeQuery("select game.gameName, sum(quantity) as totalQuantity, sum(quantity*priceAtPurchase) as totalRevenue "+
                "from orderdetail "+
                "join game on orderdetail.gameID=game.gameID "+
                "join orders on orderdetail.orderID=orders.orderID "+
                "where state = 'Đã giao' and orderDate between ?1 and ?2 "+
                "group by orderdetail.gameID "+
                "order by totalQuantity desc")
    Object[] statisticGameRevenue(LocalDate startDate, LocalDate endDate);

    @NativeQuery("select gameName, quantity, orderDate "+
                "from orders "+
                "join orderdetail on orders.orderID = orderdetail.orderID "+
                "join game on orderdetail.gameID = game.gameID "+
                "order by orderDate desc")
    Object[] statisticGameOrderDetailDesc();
}
