package com.mini.core.test.repository;

import com.mini.core.test.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleRepository extends MiniRepository<UserRole, Object> {


} 
