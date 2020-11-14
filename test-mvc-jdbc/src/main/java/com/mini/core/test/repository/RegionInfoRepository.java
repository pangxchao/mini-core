package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniJdbcIndexedRepository;
import com.mini.core.test.entity.RegionInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("regionInfoRepository")
public interface RegionInfoRepository extends PagingAndSortingRepository<RegionInfo, Long> {

} 
