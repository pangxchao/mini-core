package com.mini.core.test.repository;

import com.mini.core.test.entity.QRegionInfo;
import com.mini.core.test.entity.QUserInfo;
import com.mini.core.test.entity.UserInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Repository("userInfoRepository")
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long>, QuerydslPredicateExecutor<UserInfo> {

    default List<UserInfo> query(EntityManager entityManager) {
        QUserInfo qUserInfo = QUserInfo.userInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUserInfo.age.gt(10));
        builder.and(qUserInfo.email.startsWith("1"));


        QRegionInfo qRegionInfo = QRegionInfo.regionInfo;


        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        queryFactory.select(qUserInfo.age).from(qUserInfo)
                .join(qRegionInfo)
                .on(qUserInfo.regionId.eq(qRegionInfo.id))
                .where(
                        qUserInfo.age.gt(10).and(qUserInfo.id.in(1, 2, 3))
                )
                .orderBy(qUserInfo.id.asc())
                .groupBy(qUserInfo.name);

       // Projections.bean()
        return new ArrayList<>();
    }

} 
