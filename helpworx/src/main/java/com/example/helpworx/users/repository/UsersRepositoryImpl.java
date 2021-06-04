package com.example.helpworx.users.repository;

import com.example.helpworx.users.domain.QUsers;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.dto.UserDetailResponseDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepositoryCustom{
    private final JPAQueryFactory queryFactory;



    @Override
    public UserDetailResponseDTO findUser(Long userId) {
        QUsers users = QUsers.users;
        return null;
//        return queryFactory
//
//                .select( new QUserDetailResponseDTO(
//                        users.ctmmny.id,
//                        users.id,
//                        users.userNm,
//                        users.userPwd,
//                        users.userEmail,
//                        users.userTeleno,
//                        users.isUsed
//                ))
//                .from(users)
//                .where(eqUserId(userId))
//                .fetchOne();
    }

    @Override

    public List<Users> findUserInfoList(Pageable pageable, Long userId, String userNm, String ctmmnyCd, Boolean isUsed) {

        QUsers users = QUsers.users;

        return queryFactory
                .selectFrom(users)
                .where(
                        eqUserId(userId),
                        equserNm(userNm),
                        eqCtmmnyCd(ctmmnyCd),
                        eqIsUsed(isUsed)
                )
                .orderBy(users.id.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public List<Users> findAllByCtmmnyCd(String ctmmnyCd) {
        QUsers user = QUsers.users;
        return queryFactory
                .selectFrom(user)
                .where(eqCtmmnyCd(ctmmnyCd))
                .fetch();
    }

    private BooleanExpression eqIsUsed(Boolean isUsed) {
        return !StringUtils.isEmpty(isUsed) ? QUsers.users.isUsed.eq(isUsed) : null;
    }
    private BooleanExpression eqUserId(Long userId){
        return !StringUtils.isEmpty(userId) ? QUsers.users.id.eq(userId) : null;
    }
    private BooleanExpression equserNm(String userNm){
        return !StringUtils.isEmpty(userNm) ? QUsers.users.userNm.eq(userNm) : null;
    }
    private BooleanExpression eqCtmmnyNm(String ctmmnyNm){
        return !StringUtils.isEmpty(ctmmnyNm) ? QUsers.users.ctmmny.ctmmnyNm.eq(ctmmnyNm) : null;
    }
    private BooleanExpression eqCtmmnyCd(String ctmmnyCd){
        return !StringUtils.isEmpty(ctmmnyCd) ? QUsers.users.ctmmny.id.eq(ctmmnyCd) : null;
    }
}
