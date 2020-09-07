package com.mini.core.jpa.repository;

import com.mini.core.test.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    int deleteByName(String name);


}
