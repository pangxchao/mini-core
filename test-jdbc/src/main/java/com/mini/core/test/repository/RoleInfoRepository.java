package com.mini.core.test.repository;

import com.mini.core.test.entity.RoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleInfoRepository extends MiniRepository<RoleInfo, Long> {

}
