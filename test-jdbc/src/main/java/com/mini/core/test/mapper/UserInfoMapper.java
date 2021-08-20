package com.mini.core.test.mapper;

import com.mini.core.jdbc.mybatis.MiniMybatisMapper;
import com.mini.core.test.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author pangchao
 */
@Mapper
public interface UserInfoMapper extends MiniMybatisMapper<UserInfo> {

    List<UserInfo> findByAgeLessThanEqual(Integer age);
}




