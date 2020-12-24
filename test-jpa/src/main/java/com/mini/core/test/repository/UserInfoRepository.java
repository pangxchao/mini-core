package com.mini.core.test.repository;

import com.mini.core.test.entity.UserInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Path;
import java.util.List;


@Repository("userInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    default List<UserInfo> query(EntityManager entityManager) {
//        QUserInfo qUserInfo = QUserInfo.userInfo;
//        BooleanBuilder builder = new BooleanBuilder();
//        builder.and(qUserInfo.age.gt(10));
//        builder.and(qUserInfo.email.startsWith("1"));

        return findAll((Specification<UserInfo>) (root, query, builder) -> {
            Path<Number> path = root.get("path");
            return builder.ge(path, 5);
        });
    }

} 
