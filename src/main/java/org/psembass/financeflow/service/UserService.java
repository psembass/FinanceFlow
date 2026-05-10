package org.psembass.financeflow.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.psembass.financeflow.dto.UserRequest;
import org.psembass.financeflow.dto.UserResponse;
import org.psembass.financeflow.entity.User;
import org.psembass.financeflow.mapper.UserMapper;
import org.psembass.financeflow.repo.UsersRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepo repo;
    private final UserMapper userMapper;

    public List<UserResponse> getUsers() {
        return repo.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if (repo.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException(String.format("Email %s already exists", userRequest.getEmail()));
        }
        User entity = userMapper.toEntity(userRequest);
        return userMapper.toResponse(repo.save(entity));
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        if (!user.getEmail().equals(userRequest.getEmail())) {
            repo.findByEmail(userRequest.getEmail()).ifPresent(existing -> {
                throw new IllegalArgumentException(String.format("Email %s already exists", userRequest.getEmail()));
            });
        }
        userMapper.updateEntity(user, userRequest);
        return userMapper.toResponse(repo.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        repo.deleteById(id);
    }

    public UserResponse getUser(Long id) {
        User user = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return userMapper.toResponse(user);
    }
}
