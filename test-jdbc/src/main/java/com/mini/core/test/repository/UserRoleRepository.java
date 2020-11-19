package com.mini.core.test.repository;

import com.mini.core.test.entity.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("userRoleRepository")
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, UserRole.ID> {

} 
