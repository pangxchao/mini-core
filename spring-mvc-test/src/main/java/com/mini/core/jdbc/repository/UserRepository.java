package com.mini.core.jdbc.repository;

import com.mini.core.jdbc.entity.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends PagingAndSortingRepository<UserInfo, Long> {
}
