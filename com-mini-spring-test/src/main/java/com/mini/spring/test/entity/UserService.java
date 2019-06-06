package com.mini.spring.test.entity;

import com.mini.spring.test.entity.UserDao;
import org.springframework.transaction.annotation.Transactional;


public interface UserService {

    UserDao getUserDao();



}
