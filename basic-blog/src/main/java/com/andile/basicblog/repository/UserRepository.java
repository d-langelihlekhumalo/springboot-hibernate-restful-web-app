package com.andile.basicblog.repository;

import com.andile.basicblog.entity.User;
import com.andile.basicblog.payload.RegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

//    RegisterDTO findById(long id);

    Boolean existsByEmail(String email);

}
