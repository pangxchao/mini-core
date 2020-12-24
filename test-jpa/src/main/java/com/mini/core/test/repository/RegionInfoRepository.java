package com.mini.core.test.repository;

import com.mini.core.test.entity.RegionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("regionInfoRepository")
public interface RegionInfoRepository extends JpaRepository<RegionInfo, Long> {

} 
