package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniJdbcRepository;
import com.mini.core.test.entity.UserInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mini.core.data.builder.SelectSql.select;


@Repository("userInfoRepository")
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long>, MiniJdbcRepository, QueryByExampleExecutor<UserInfo> {

    List<UserInfo> findByEmailStartsWith(String email);

    default List<UserInfo> findByIdList(Long[] idList) {
        Example<UserInfo> example =   Example.of(new UserInfo());

        return this.queryList(select(UserInfo.class, sql -> {
            // SELECT_FROM 可以把读取实体的Class对象的字段和表名
            // 表名可以带JOIN语句，比如：user_info JOIN region_info ON user_region_id = region_id
            // 表名带JOIN语句之后，sql就不能再设置FROM语句了，防止SQL错乱
            // 表名带JOIN语句后，sql依然可以设置JOIN语句
            sql.where(where -> {
                if (idList != null && idList.length > 0) {
                    where.in(UserInfo.USER_ID, idList);
                }
            });
        }), UserInfo.class);
    }
}
