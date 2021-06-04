package com.example.helpworx.users.repository;

import com.example.helpworx.users.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long id);

    Optional<Role> findByRoleNm(String roleNm);

}
