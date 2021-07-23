package com.mini.core.test.repository;

import com.mini.core.test.entity.RegionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RegionInfoRepository extends PagingAndSortingRepository<RegionInfo, Long> {
}
