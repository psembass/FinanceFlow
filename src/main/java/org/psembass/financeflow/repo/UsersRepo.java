package org.psembass.financeflow.repo;

import org.psembass.financeflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String name);
    Optional<User> findByEmail(String email);
}
