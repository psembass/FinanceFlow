package org.psembass.financeflow.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.psembass.financeflow.dto.UserRequest;
import org.psembass.financeflow.dto.UserResponse;
import org.psembass.financeflow.entity.User;
import org.psembass.financeflow.mapper.UserMapper;
import org.psembass.financeflow.repo.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepo repo;
    private final UserMapper userMapper;

    public List<UserResponse> getUsers() {
        return repo.findAll().stream().map(userMapper::toResponse).toList();
    }

    public UserResponse createUser(@Valid UserRequest userRequest) {
        User entity = userMapper.toEntity(userRequest);
        return userMapper.toResponse(repo.save(entity));
    }

    public UserResponse updateUser(Long id, @Valid UserRequest userRequest) {
        Optional<User> userOptional = repo.findById(id);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        User user = userOptional.get();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return userMapper.toResponse(repo.save(user));
    }

    public void deleteUser(Long id) {
        repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        repo.deleteById(id);
    }

    public UserResponse getUser(Long id) {
        User user = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return userMapper.toResponse(user);
    }
}
