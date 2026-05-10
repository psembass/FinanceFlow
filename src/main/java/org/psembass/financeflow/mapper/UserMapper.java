package org.psembass.financeflow.mapper;

import org.psembass.financeflow.dto.UserRequest;
import org.psembass.financeflow.dto.UserResponse;
import org.psembass.financeflow.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

    public User toEntity(UserRequest userRequest) {
        User user = new User();
        updateEntity(user, userRequest);
        return user;
    }

    public void updateEntity(User user, UserRequest userRequest) {
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
    }
}
