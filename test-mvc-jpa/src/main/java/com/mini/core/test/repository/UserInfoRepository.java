package com.mini.core.test.repository;

import com.mini.core.test.entity.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("userInfoRepository")
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long> {

} 
