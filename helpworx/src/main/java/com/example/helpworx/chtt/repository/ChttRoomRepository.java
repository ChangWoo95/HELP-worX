package com.example.helpworx.chtt.repository;

import com.example.helpworx.chtt.domain.ChttRoom;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChttRoomRepository extends JpaRepository<ChttRoom,Long> ,ChttRoomRepositoryCustom{
//    @Override
//    <S extends ChttRoom> Page<S> findAll(Example<S> example);

    @Override
    List<ChttRoom> findAll();

    List<ChttRoom> findByUserId(Long usersId);
}
