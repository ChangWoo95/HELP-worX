package com.example.helpworx.ctmmny.repository;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.domain.QCtmmny;
import com.example.helpworx.ctmmny.domain.QCtmmnySys;
import com.example.helpworx.ctmmny.dto.CtmmnyCdAndNmResponseDTO;
import com.example.helpworx.ctmmny.dto.QCtmmnyCdAndNmResponseDTO;
import com.example.helpworx.users.domain.QUsers;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class CtmmnyRepositoryImpl implements CtmmnyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Ctmmny> findByCtmmnyCdAndCtmmnyNm(Pageable pageable,String ctmmnyCd, String ctmmnyNm, Boolean isUsed) {
        QCtmmny ctmmny = QCtmmny.ctmmny;
        QUsers users = QUsers.users;
        QCtmmnySys ctmmnySys = QCtmmnySys.ctmmnySys;

        return queryFactory
                .selectDistinct(ctmmny)
                .from(ctmmny)
                .leftJoin(ctmmny.usersList, users)
                .leftJoin(ctmmny.ctmmnySysList, ctmmnySys).fetchJoin()
                .where(
                        ctmmny.id.contains(ctmmnyCd),
                        ctmmny.ctmmnyNm.contains(ctmmnyNm),
                        eqIsUsed(isUsed)
                )
                .orderBy(ctmmny.contBeginDate.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
    @Override
    public List<CtmmnyCdAndNmResponseDTO> findListByCtmmnyCdAndCtmmnyNm() {
        QCtmmny ctmmny = QCtmmny.ctmmny;

        return queryFactory
                .select(
                        new QCtmmnyCdAndNmResponseDTO(
                                ctmmny.id,
                                ctmmny.ctmmnyNm
                        )
                )
                .from(ctmmny)
                .fetch();
    }
    private BooleanExpression eqCtmmnyCd(String code){
        return !StringUtils.isEmpty(code) ? QCtmmny.ctmmny.id.eq(code) : null;
    }

    private BooleanExpression eqCtmmnyNm(String name){
        return !StringUtils.isEmpty(name) ? QCtmmny.ctmmny.ctmmnyNm.eq(name) : null;
    }
    private BooleanExpression eqIsUsed(Boolean isUsed) {
        return !StringUtils.isEmpty(isUsed) ? QCtmmny.ctmmny.isUsed.eq(isUsed) : null;
    }
}
