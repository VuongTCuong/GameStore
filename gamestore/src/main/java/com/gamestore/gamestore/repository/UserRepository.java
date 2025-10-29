package com.gamestore.gamestore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import org.springframework.stereotype.Repository;

import com.gamestore.gamestore.dto.GetUserDTO;
import com.gamestore.gamestore.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByAccount(String account);
    
    User findByEmail(String email);
    boolean existsByAccount(String account);
    boolean existsByEmail(String account);

    @NativeQuery("select userID, fullName, account, email, role, state from user")
    List<GetUserDTO> findAll_noPassword();
  
}
