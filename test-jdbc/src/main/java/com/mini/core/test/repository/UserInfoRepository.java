package com.mini.core.test.repository;

import com.mini.core.test.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@Mapper
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long> {

    List<UserInfo> findByName(@Param("name") String name);

}
