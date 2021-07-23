package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniRepository;
import com.mini.core.test.entity.TextInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface TextInfoRepository extends MiniRepository<TextInfo, Long> {


}
