package com.example.helpworx.ctmmny.repository;

import com.example.helpworx.ctmmny.domain.Sys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRepository extends JpaRepository<Sys,Long> {

}
