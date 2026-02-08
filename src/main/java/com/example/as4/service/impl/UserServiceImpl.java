package com.example.as4.service.impl;

import com.example.as4.model.dto.UserDTO;
import com.example.as4.model.entity.User;
import com.example.as4.repository.UserRepository;
import com.example.as4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(UserDTO dto) {
        validate(dto);

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("User with username already exists: " + dto.getUsername());
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .age(dto.getAge())
                .balance(dto.getBalance())
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(Long id, UserDTO dto) {
        validate(dto);

        User existing = getById(id);

        if (!existing.getUsername().equals(dto.getUsername())
                && userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("User with username already exists: " + dto.getUsername());
        }

        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());
        existing.setBalance(dto.getBalance());

        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        User existing = getById(id);
        userRepository.delete(existing);
    }

    private void validate(UserDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().isBlank()) {
            throw new RuntimeException("username is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new RuntimeException("email is required");
        }
        if (dto.getAge() != null && dto.getAge() < 0) {
            throw new RuntimeException("age cannot be negative");
        }
        if (dto.getBalance() != null && dto.getBalance() < 0) {
            throw new RuntimeException("balance cannot be negative");
        }
    }
}