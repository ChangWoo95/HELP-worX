package com.example.helpworx.sr.repository;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.sr.domain.Sr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SrRepository extends JpaRepository<Sr, Long>, SrRepositoryCustom {

    @Override
    List<Sr> findAll();
    Long countByCtmmny(Ctmmny ctmmny);
}
