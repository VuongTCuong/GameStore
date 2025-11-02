package com.gamestore.gamestore.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.dto.GetUserDTO;
import com.gamestore.gamestore.dto.UpdateUserDTO;
import com.gamestore.gamestore.entity.Review;
import com.gamestore.gamestore.entity.User;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.OrdersRepository;
import com.gamestore.gamestore.repository.ReviewRepository;
import com.gamestore.gamestore.repository.UserRepository;


@Service

public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private OrdersRepository orderRepo;

    private final PasswordEncoder passwordEncoder;
    public UserService(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    // lấy thông tin user
    public Map<String,Object> getUserInfo(UserDetails userDetails){
  
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));
        Map<String,Object> responsebody = Map.of(
            "Full name",user.getFullName(),
            "Account",user.getAccount(),
            "Email",user.getEmail()
        );
        return responsebody;
    }


    //Cập nhật thông tin user - của user
    public Map<String,Object> updateUserInfo(UserDetails userDetails, UpdateUserDTO updateUser){
        User user_temp = userRepo.findByEmail(updateUser.getEmail());
        if(userRepo.existsByEmail(updateUser.getEmail()) && !user_temp.getAccount().equals(userDetails.getUsername())){
            throw new MainErrorException("Email đã tồn tại");
        }
     
        if(!updateUser.getPassword().equals(updateUser.getConfirmPassword())){
            throw new MainErrorException("Mật khẩu và Xác nhận mật khẩu không trùng khớp");
        }

        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()-> new MainErrorException("Không tìm thấy tài khoản"));
        if(passwordEncoder.matches(updateUser.getPassword(), user.getPassword())){
            throw new MainErrorException("Mật khẩu không thể trùng mật khẩu cũ");
        }
        user.setEmail(updateUser.getEmail());
        user.setFullName(updateUser.getFullName());
        user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        userRepo.save(user);
        Map<String,Object> responsebody = Map.of(
            "status","thành công",
            "message","cập nhật thông tin người dùng thành công"
        );

        return responsebody;
    }

    // lấy thông tin tất cả user - của admin
    public List<GetUserDTO> getAllUser(){
        List<GetUserDTO> users = userRepo.findAll_noPassword();
        return users;
    }
    
    // cập nhật trạng thái user - của admin
    public Map<String,Object> updateUserState(String account, String state){
        User user = userRepo.findByAccount(account).orElseThrow(()-> new MainErrorException("Không tìm thấy thông tin người dùng"));
        user.setState(state);
        userRepo.save(user);
        return Map.of(
            "status","thành công",
            "message","cập nhật trạng thái người dùng thành công"
        );
    }

    //xoá user (chỉ khi user đó chưa mua đơn hàng nào) - của admin
    public Map<String,Object> deleteUser(Integer userID){
       
        if(orderRepo.existsByUserID(userID)){
            throw new MainErrorException("Không thể xoá người dùng vì vẫn còn đơn hàng");
        }   
        User user = userRepo.findById(userID).orElseThrow(()-> new MainErrorException("Không tìm thấy thông tin người dùng"));

        List<Review> review = reviewRepo.findByUserID(userID);
        for (Review r : review) {
            r.setUserID(null);
        }
        reviewRepo.saveAll(review);
        userRepo.delete(user);

        return Map.of(
            "status","thành công",
            "message","xoá người dùng thành công"
        );
    }
}
