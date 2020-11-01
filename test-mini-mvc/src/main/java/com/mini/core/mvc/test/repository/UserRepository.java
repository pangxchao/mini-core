package com.mini.core.mvc.test.repository;

import com.mini.core.mvc.test.MiniTestNamedRepository;
import com.mini.core.mvc.test.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends PagingAndSortingRepository<UserInfo, Long>, MiniTestNamedRepository<UserInfo> {

    @Query("select * from user_info where user_name = :name ")
    List<UserInfo> findByName(@Param("name") String name);

    @Query("select * from user_info where user_full_name like :fullName")
    List<UserInfo> findByFullNameLike(@Param("fullName") String fullName);

    @Query("select * from user_info where user_full_name like :fullName")
    Page<UserInfo> findByFullNameLike(Pageable pageable, @Param("fullName") String fullName);

    @Query("select * from user_info where user_email like concat(:email, '%')")
    List<UserInfo> findByEmailLike(@Param("email") String email);

//    default List<UserInfo> find() {
//        return queryList(of().SELECT_FROM(UserInfo.class),
//                UserInfo.class);
//    }
}
