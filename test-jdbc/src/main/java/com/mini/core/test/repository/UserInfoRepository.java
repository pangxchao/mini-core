package com.mini.core.test.repository;

import com.mini.core.data.builder.SelectSql;
import com.mini.core.jdbc.MiniJdbcRepository;
import com.mini.core.test.entity.UserInfo;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("userInfoRepository")
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long>, MiniJdbcRepository {

    @Query
    default List<UserInfo> findByIdList(Long[] idList, SQLQueryFactory queryFactory) {
        return this.queryList(SelectSql.of(sql -> {

            // SELECT_FROM 可以把读取实体的Class对象的字段和表名
            // 表名可以带JOIN语句，比如：user_info JOIN region_info ON user_region_id = region_id
            // 表名带JOIN语句之后，sql就不能再设置FROM语句了，防止SQL错乱
            // 表名带JOIN语句后，sql依然可以设置JOIN语句
            sql.selects(UserInfo.USER_INFO + ".*");
            sql.from(UserInfo.USER_INFO);
            sql.where(where -> {
                if (idList != null && idList.length > 0) {
                    where.in(UserInfo.USER_ID, idList);
                }
            });
        }), UserInfo.class);
    }
}
