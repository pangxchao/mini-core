package com.mini.core.test.repository;

import com.mini.core.test.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("roleInfoRepository")
public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {

} 
