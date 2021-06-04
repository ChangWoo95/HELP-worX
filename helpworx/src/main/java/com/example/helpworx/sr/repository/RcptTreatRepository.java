package com.example.helpworx.sr.repository;

import com.example.helpworx.sr.domain.RcptTreat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RcptTreatRepository extends JpaRepository<RcptTreat,Long>{
    Optional<RcptTreat> findById(String id);
}