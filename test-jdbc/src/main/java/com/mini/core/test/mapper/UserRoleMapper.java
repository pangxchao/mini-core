package com.mini.core.test.mapper;

import com.mini.core.jdbc.mybatis.MiniMybatisMapper;
import com.mini.core.test.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pangchao
 */
@Mapper
public interface UserRoleMapper extends MiniMybatisMapper<UserRole> {

}




