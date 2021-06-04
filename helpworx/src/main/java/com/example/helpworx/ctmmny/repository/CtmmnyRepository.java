package com.example.helpworx.ctmmny.repository;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CtmmnyRepository extends JpaRepository<Ctmmny, String>, CtmmnyRepositoryCustom {
//    Long delete(String ctmmnyCd);
    void deleteById(String ctmmnyCd);

//    void delete(String ctmmnyCd);
}
