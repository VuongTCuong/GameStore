package com.gamestore.gamestore.service;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.gamestore.gamestore.dto.CreateUserDTO;
import com.gamestore.gamestore.dto.LoginUserDTO;
import com.gamestore.gamestore.entity.Cart;
import com.gamestore.gamestore.entity.User;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.CartRepository;
import com.gamestore.gamestore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private UserRepository userRepo;
    
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
   
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CartRepository cartRepo;

    //Đăng ký
    public Map<String,Object> CreateUser(CreateUserDTO userInfo){
        //kiểm tra tồn tại của người dùng
        if(userRepo.existsByAccount(userInfo.getAccount())){
            throw new MainErrorException("Tài khoản đã tồn tại");
        }
        if(userRepo.existsByEmail(userInfo.getEmail())){
            throw new MainErrorException("Email đã tồn tại");
        }

        //map data từ trường dữ liệu sang đối tượng user
        User user = new User();
        
        user.setFullName(userInfo.getFullName());
        user.setAccount(userInfo.getAccount());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setEmail(userInfo.getEmail());
        user.setRole("ROLE_CUSTOMER");
        user.setState("UNLOCK");
        userRepo.save(user);


        Cart cart = new Cart();
        cart.setUserID(user.getUserID());
        cartRepo.save(cart);

        //thiết kế response trả về 
        Map<String,String> dataResponse = Map.of(
            "username", userInfo.getFullName(),
            "email",userInfo.getEmail()
        );

        Map<String,Object> responseBody = Map.of(
            "status", "thành công",
            "message", "đăng ký thành công",
            "data",dataResponse
        );

        return responseBody;
    }
    
    //Đăng nhập - xác thực
    public Map<String,Object> Authentication(LoginUserDTO userInfo){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo.getAccount(), userInfo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        
        User user = userRepo.findByAccount(userInfo.getAccount()).orElseThrow(() -> new MainErrorException("Không tìm thấy người dùng"));
            
            //thiết kế response trả về
        Map<String,Object> responsebody = Map.of(
                "status","thành công",
                "message","đăng nhập thành công",
                "role", user.getRole(),
                "key", jwtService.generateToken(userInfo.getAccount())
            );
            
        return responsebody;
        
    }

    public Map<String,Object> LoginJwtToken(UserDetails userDetails){
        User user = userRepo.findByAccount(userDetails.getUsername()).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));

        Map<String,Object> responsebody =  Map.of(
            "message","thành công"
        );
        
        return responsebody;
    }
}
