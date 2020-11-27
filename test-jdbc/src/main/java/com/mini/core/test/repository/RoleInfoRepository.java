package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniJdbcRepository;
import com.mini.core.test.entity.RoleInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("roleInfoRepository")
public interface RoleInfoRepository extends PagingAndSortingRepository<RoleInfo, Long>, MiniJdbcRepository {

} 
