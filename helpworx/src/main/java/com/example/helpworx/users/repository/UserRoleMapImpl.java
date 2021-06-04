package com.example.helpworx.users.repository;

import com.example.helpworx.users.domain.QUserRoleMap;
import com.example.helpworx.users.domain.QUsers;
import com.example.helpworx.users.domain.UserRoleMap;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class UserRoleMapImpl implements UserRoleMapRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserRoleMap> findListByUserId(Long userId) {
        QUserRoleMap userRoleMap = QUserRoleMap.userRoleMap;
        return queryFactory
                .selectFrom(userRoleMap)
                .where(
                        eqUserId(userId)
                )
                .fetch();
//        LoggerFactory.getLogger(this.getClass()).info("role 확인" + roleMaps);
//        List<Long> ids = new ArrayList<>();
//
//        for(UserRoleMap map : roleMaps ) {
//            ids.add(map.getRole().getId());
//        }
//        return ids;
    }

    private BooleanExpression eqUserId(Long userId){
        return !StringUtils.isEmpty(userId) ? QUserRoleMap.userRoleMap.user.id.eq(userId) : null;
    }
}
