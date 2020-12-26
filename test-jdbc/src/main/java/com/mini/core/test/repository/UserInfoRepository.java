package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniRepository;
import com.mini.core.test.entity.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository("userInfoRepository")
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long>, MiniRepository {

    List<UserInfo> findByEmailStartsWith(String email);

    List<UserInfo> findByIdIn(Collection<Long> id);
}
