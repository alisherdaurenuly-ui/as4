package com.example.as4.service;

import com.example.as4.model.dto.UserDTO;
import com.example.as4.model.entity.User;

import java.util.List;

public interface UserService {
    User create(UserDTO dto);
    User getById(Long id);
    List<User> getAll();
    User update(Long id, UserDTO dto);
    void delete(Long id);
}