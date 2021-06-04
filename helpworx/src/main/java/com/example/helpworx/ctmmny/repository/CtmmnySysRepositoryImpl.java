package com.example.helpworx.ctmmny.repository;

import com.example.helpworx.ctmmny.domain.QCtmmny;
import com.example.helpworx.ctmmny.domain.QCtmmnySys;
import com.example.helpworx.ctmmny.dto.QSystemInfoDTO;
import com.example.helpworx.ctmmny.dto.SystemInfoDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class CtmmnySysRepositoryImpl implements CtmmnySysRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Long deleteByCtmmnyCd(String ctmmnyCd) {
        QCtmmnySys ctmmnySys = QCtmmnySys.ctmmnySys;

        return queryFactory
                .delete(ctmmnySys)
                .where(
                        eqCtmmnyCd(ctmmnyCd)
                )
                .execute();
    }

    @Override
    public List<Long> findIdListByCtmmnyCd(String ctmmnyCd) {
        QCtmmnySys ctmmnySys = QCtmmnySys.ctmmnySys;
        return queryFactory
                .select(ctmmnySys.sys.id)
                .from(ctmmnySys)
                .where(
                        eqCtmmnyCd(ctmmnyCd)
                )
                .fetch();
    }

    @Override
    public List<SystemInfoDTO> findSysNameListbyCtmmnyCd(String ctmmnyCd) {
        QCtmmnySys ctmmnySys = QCtmmnySys.ctmmnySys;

        return queryFactory
                .select(
                        new QSystemInfoDTO(
                                ctmmnySys.sys.id,
                                ctmmnySys.sys.sysNm
                        ))
                .from(ctmmnySys)
                .where(
                        eqCtmmnyCd(ctmmnyCd)
                )
                .fetch();
    }
    private BooleanExpression eqCtmmnyCd(String code){
        return !StringUtils.isEmpty(code) ? QCtmmny.ctmmny.id.eq(code) : null;
    }
}
