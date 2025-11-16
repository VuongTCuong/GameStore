package com.gamestore.gamestore.service;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.dto.CreateCartDetailDTO;
import com.gamestore.gamestore.dto.UpdateOrderDTO;
import com.gamestore.gamestore.entity.Cart;
import com.gamestore.gamestore.entity.CartDetailID;
import com.gamestore.gamestore.entity.Game;
import com.gamestore.gamestore.entity.Invoice;
import com.gamestore.gamestore.entity.OrderDetail;
import com.gamestore.gamestore.entity.Orders;
import com.gamestore.gamestore.entity.User;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.CartDetailRepository;
import com.gamestore.gamestore.repository.CartRepository;
import com.gamestore.gamestore.repository.GameRepository;
import com.gamestore.gamestore.repository.InvoiceRepository;
import com.gamestore.gamestore.repository.OrderDetailRepository;
import com.gamestore.gamestore.repository.OrdersRepository;
import com.gamestore.gamestore.repository.UserRepository;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartDetailRepository cartDetailRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private InvoiceRepository invoiceRepo;

    // lấy đơn hàng của bản thân - của customer
    public List<Orders> getOrders(UserDetails userDetails){
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));
        List<Orders> orders = ordersRepo.findByUserID(user.getUserID());
        return orders;
    }

    // lấy đơn hàng của tất cả customer - của admin
    public List<Orders> getAllOrders(){
        return ordersRepo.findAll();
    }

    //tạo đơn hàng - của user
    public Map<String,Object> addOrder(UserDetails userDetails, List<CreateCartDetailDTO> cartDetailDTOs){ // sử dụng lại CreateCartDetailDTO vì đủ thông tin mà order cần (gameID, price, quantity)
        Cart cart = cartService.getCartbyUser(userDetails);
        User user = userRepo.findByAccount(userDetails.getUsername()    ).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));
        
        // tạo đơn hàng
        Orders order =  new Orders();

        order.setTotalAmount(0);
        order.setOrderDate(LocalDate.now());
        order.setState("Chờ xác nhận");
        order.setUserID(user.getUserID());
        ordersRepo.save(order);

        float totalAmount = 0; 
        List<OrderDetail> orderDetails = new ArrayList<>();
        
        for (CreateCartDetailDTO c : cartDetailDTOs) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderID(order.getOrderID());
            orderDetail.setGameID(c.getGameID());
            orderDetail.setPriceAtPurchase(c.getPriceAtPurchase());
            orderDetail.setQuantity(c.getQuantity());
            
            orderDetails.add(orderDetail);

            
            // tính tổng tiền
            totalAmount += c.getPriceAtPurchase()*c.getQuantity();

            // xoá khỏi giỏ hàng
            cartDetailRepo.deleteById(new CartDetailID(cart.getCartID(), c.getGameID()));

            // trừ số trò chơi tồn
            Game game = gameRepo.findById(c.getGameID()).orElseThrow(()-> new MainErrorException("Không tìm thấy mã trò chơi"));
            game.setStockQuantity(game.getStockQuantity()-c.getQuantity());
            gameRepo.save(game);
        }
    
        //tạo đơn hàng
        order.setTotalAmount(totalAmount);
        ordersRepo.save(order);
        orderDetailRepo.saveAll(orderDetails);
        
        cart.setUpdateDate(LocalDate.now());
        cartRepo.save(cart);

        //tạo hoá đơn
        Invoice invoice = new Invoice();
        invoice.setOrderID(order.getOrderID());
        invoice.setPaymentStatus("Chờ thanh toán");
        invoiceRepo.save(invoice);
        
        return Map.of(
            "status", "thành công",
            "message", "tạo đơn hàng thành công"
        );
    }
    // cập nhật trạng thái đơn hàng - của admin
    public Map<String,Object> updateOrder(UpdateOrderDTO updateOrderDTO){

        Orders order  = ordersRepo.findById(updateOrderDTO.getOrderID()).orElseThrow(()-> new MainErrorException("Mã đơn hàng không tồn tại"));
        order.setDeliveryDate(updateOrderDTO.getDeliveryDate());
        order.setState(updateOrderDTO.getState());
        
        ordersRepo.save(order);
        return Map.of(
            "status", "thành công",
            "message", "cập nhật đơn hàng thành công"
        );
    }

    // xoá đơn hàng - customer huỷ khi chưa được xác nhận bởi admin
    public Map<String,Object> cancelOrder(UserDetails userDetails, Integer orderID){
        String authority = userDetails.getAuthorities().iterator().next().toString();
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));
        
        Orders order  = ordersRepo.findById(orderID).orElseThrow(()-> new MainErrorException("Mã đơn hàng không tồn tại"));
        
        if(user.getUserID()!=order.getUserID() && authority.equals("ROLE_CUSTOMER")){
            throw new MainErrorException("Bạn không có quyền xoá đơn hàng này");
        }
        
        if(!order.getState().equals("Chờ xác nhận")){
            throw new MainErrorException("Không thể huỷ đơn hàng hiện tại");
        }
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderID(orderID);
        for (OrderDetail o: orderDetails) {
            Game game = gameRepo.findById(o.getGameID()).orElseThrow(()-> new MainErrorException("Không tìm thấy mã trò chơi"));
            game.setStockQuantity(game.getStockQuantity()+o.getQuantity());
            gameRepo.save(game);
        }
        ordersRepo.delete(order);


        // huỷ hoá đơn
        Invoice invoice = invoiceRepo.findByOrderID(orderID).orElseThrow(()-> new MainErrorException("Không tìm thấy mã đơn hàng"));
        invoiceRepo.delete(invoice);

        
        return Map.of(
            "status", "thành công",
            "message", "huỷ đơn hàng thành công"
        );
    }
}
