package com.andile.basicblog.service;

import com.andile.basicblog.entity.User;
import com.andile.basicblog.payload.UserDTO;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(long id);

    UserDTO update (User user, Principal principal);

    void deleteById(long id, Principal principal);
}
