package com.mini.spring.test.entity;

import com.mini.util.dao.IDao;
import com.mini.util.dao.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private final IDao daoTemplate;
    private final IMapper<User> userMapper;

    @Autowired
    public UserDaoImpl(IDao daoTemplate, IMapper<User> userMapper) {
        this.daoTemplate = daoTemplate;
        this.userMapper  = userMapper;
    }

    @Override
    public IDao getWriteDao() {
        return daoTemplate;
    }

    @Override
    public IDao getReadDao() {
        return daoTemplate;
    }

    @Override
    public IMapper<User> getUserMapper() {
        return userMapper;
    }
}
