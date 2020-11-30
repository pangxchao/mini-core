package com.mini.core.test.repository;

import com.mini.core.test.entity.QUserInfo;
import com.mini.core.test.entity.UserInfo;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Repository("userInfoRepository")
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    default List<UserInfo> query(EntityManager entityManager) {
        QUserInfo qUserInfo = QUserInfo.userInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUserInfo.age.gt(10));
        builder.and(qUserInfo.email.startsWith("1"));

        // Projections.bean()
        return new ArrayList<>();
    }

} 
