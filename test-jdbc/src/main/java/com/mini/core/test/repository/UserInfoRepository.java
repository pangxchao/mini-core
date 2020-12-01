package com.mini.core.test.repository;

import com.mini.core.data.builder.SelectSql;
import com.mini.core.jdbc.MiniJdbcRepository;
import com.mini.core.test.entity.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("userInfoRepository")
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long>, MiniJdbcRepository {

    List<UserInfo> findByEmailStartsWith(String email);

    default List<UserInfo> findByIdList(Long[] idList) {
        return queryList(new SelectSql(UserInfo.class) {{
            if (idList != null && idList.length > 0) {
                where(it -> it.in(UserInfo.USER_ID, idList));
            }
        }}, UserInfo.class);
    }
}
