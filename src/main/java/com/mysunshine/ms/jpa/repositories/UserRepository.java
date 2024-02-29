package com.mysunshine.ms.jpa.repositories;

import com.mysunshine.ms.jpa.CustomJpaRepository;
import com.mysunshine.ms.jpa.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CustomJpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}