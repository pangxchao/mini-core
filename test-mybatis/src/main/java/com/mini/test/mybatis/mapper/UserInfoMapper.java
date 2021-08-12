package com.mini.test.mybatis.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.mini.test.mybatis.domain.UserInfo;
import com.mini.core.mybatis.MiniMybatisMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* 
* @author pangchao
*/
@Mapper
public interface UserInfoMapper extends MiniMybatisMapper<UserInfo> {

    List<UserInfo> findByAgeLessThanEqual(Integer age);
}




