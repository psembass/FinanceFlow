package org.psembass.financeflow.repo;

import org.psembass.financeflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User, Long> {
}
