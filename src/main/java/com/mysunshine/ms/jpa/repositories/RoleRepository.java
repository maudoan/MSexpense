package com.mysunshine.ms.jpa.repositories;

import com.mysunshine.ms.constant.enums.ERole;
import com.mysunshine.ms.jpa.CustomJpaRepository;
import com.mysunshine.ms.jpa.models.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends CustomJpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}