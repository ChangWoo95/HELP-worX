package com.example.helpworx.ctmmny.repository;

import com.example.helpworx.ctmmny.domain.CtmmnySys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CtmmnySysRepository extends JpaRepository<CtmmnySys,Long>, CtmmnySysRepositoryCustom {
    @Override
    Optional<CtmmnySys> findById(Long id);

}