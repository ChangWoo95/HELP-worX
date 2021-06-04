package com.example.helpworx.chtt.repository;

import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.chtt.domain.QChttRoom;
import com.example.helpworx.chtt.dto.ChttRoomDto;
import com.example.helpworx.sr.domain.QSr;
import com.example.helpworx.sr.dto.QSrDto_ListResponse;
import com.example.helpworx.sr.dto.SrDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ChttRoomRepositoryImpl implements ChttRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;


        @Override
        public List<ChttRoom> findChttRoomListWithCondition(Pageable pageable, ChttRoomDto chttRoomDto) {
            QChttRoom chttRoom = QChttRoom.chttRoom;

            return  queryFactory
                    .select(chttRoom)
                    .from(chttRoom)
                    .where(
                            chttRoom.sysNm.contains(chttRoomDto.getSysNm()),
                            chttRoom.status.contains(chttRoomDto.getStatus())
                )
                    .orderBy(chttRoom.id.asc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())

                    .fetch();


    }
}
