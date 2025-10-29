package com.gamestore.gamestore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.dto.CreateCartDetailDTO;
import com.gamestore.gamestore.entity.Cart;
import com.gamestore.gamestore.entity.CartDetail;
import com.gamestore.gamestore.entity.Game;
import com.gamestore.gamestore.entity.User;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.CartDetailRepository;
import com.gamestore.gamestore.repository.CartRepository;
import com.gamestore.gamestore.repository.GameRepository;
import com.gamestore.gamestore.repository.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartDetailRepository cartDetailRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GameRepository gameRepo;

    // lấy giỏ hàng bằng ID người dùng - sub function
    public Cart getCartbyUser(UserDetails userDetails){
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));
        Cart cart = cartRepo.findByUserID(user.getUserID());
        return cart;
    }

    // lấy thông tin giỏ hàng của bản thân
    public List<CartDetail> getCartDetailbyUser(UserDetails userDetails){
        Cart cart = getCartbyUser(userDetails);
        List<CartDetail> cartDetail = cartDetailRepo.findByCartID(cart.getCartID());

        return cartDetail;
    }

    // thêm game vào giỏ hàng

    public Map<String,Object> addtoCart(UserDetails userDetails, CreateCartDetailDTO createCartDetailDTO){
        
        Cart cart = getCartbyUser(userDetails);

        if(createCartDetailDTO.getPriceAtPurchase()<=0){
            throw new MainErrorException("Giá không hợp lệ");
        }
      
        Game game = gameRepo.findById(createCartDetailDTO.getGameID()).orElseThrow(()-> new MainErrorException("Mã trò chơi không hợp lệ"));

        if(game.getStockQuantity()<createCartDetailDTO.getQuantity() || createCartDetailDTO.getQuantity()<=0){
            throw new MainErrorException("Số lượng không hợp lệ");
        }

        CartDetail cartDetail = new CartDetail();
        cartDetail.setCartID(cart.getCartID());
        cartDetail.setGameID(createCartDetailDTO.getGameID());
        cartDetail.setPriceAtPurchase(createCartDetailDTO.getPriceAtPurchase());
        cartDetail.setQuantity(createCartDetailDTO.getQuantity());
     
        cartDetailRepo.save(cartDetail);

        cart.setUpdateDate(LocalDate.now());
        cartRepo.save(cart);
        return Map.of(
            "status", "thành công",
            "message", "thêm vào giỏ hàng thành công",
            "result",cartDetail
        );
    }

    // cập nhật thông tin của giỏ hàng

    public Map<String,Object> updateCart(UserDetails userDetails, CreateCartDetailDTO updatecartDetail){
        Cart cart = getCartbyUser(userDetails);

        if(updatecartDetail.getPriceAtPurchase()<=0){
            throw new MainErrorException("Giá không hợp lệ");
        }
      
        Game game = gameRepo.findById(updatecartDetail.getGameID()).orElseThrow(()-> new MainErrorException("Mã trò chơi không hợp lệ"));
        if(game.getStockQuantity()<updatecartDetail.getQuantity() || updatecartDetail.getQuantity()<=0){
            throw new MainErrorException("Số lượng không hợp lệ");
        }

        CartDetail cartDetail = cartDetailRepo.findByCartIDAndGameID(cart.getCartID(),updatecartDetail.getGameID()).orElseThrow(()-> new MainErrorException("Mã giỏ hàng không hợp lệ"));

        cartDetail.setQuantity(updatecartDetail.getQuantity());
        cartDetailRepo.save(cartDetail);

        cart.setUpdateDate(LocalDate.now());
        cartRepo.save(cart);

        return Map.of(
            "status", "thành công",
            "message", "cập nhật giỏ hàng thành công"
        );

    }

    // xoá một sản phẩm giỏ hàng
    public Map<String,Object> deleteCart(UserDetails userDetails, Integer gameID){
        gameRepo.findById(gameID).orElseThrow(()-> new MainErrorException("Mã trò chơi không hợp lệ"));
        Cart cart = getCartbyUser(userDetails);
        
        CartDetail cartDetail = cartDetailRepo.findByCartIDAndGameID(cart.getCartID(), gameID).orElseThrow(()-> new MainErrorException("Không tìm thấy thông tin giỏ hàng để xoá"));
        cartDetailRepo.delete(cartDetail);

        cart.setUpdateDate(LocalDate.now());
        cartRepo.save(cart);
        return Map.of(
            "status", "thành công",
            "message", "xoá khỏi giỏ hàng thành công"
        );

    }
}
