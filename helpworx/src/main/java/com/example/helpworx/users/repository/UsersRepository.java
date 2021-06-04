package com.example.helpworx.users.repository;

import com.example.helpworx.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long>, UsersRepositoryCustom {
    Optional<Users> findOneWithRolesByUserEmail(String userEmail);
    Optional<Users> findById(Long id);
}
