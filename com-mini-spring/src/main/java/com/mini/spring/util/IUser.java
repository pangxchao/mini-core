package com.mini.spring.util;


import com.mini.util.dao.PKGenerator;

import java.io.Serializable;

/**
 * IUser
 * @author XChao
 */
public interface IUser extends Serializable {
    String USER_KEY = "MINI_USER_SESSION_KEY";

    /**
     * 获取用户ID
     * @return the id
     */
    long getId();

    /**
     * 重新创建用户token
     * @param uid 用户ID
     * @return 根据用户ID生成的用户TOKEN
     */
    static String newToken(long uid) {
        return PKGenerator.random(17) + Long.toHexString(uid).toUpperCase();
    }

    /**
     * 解码token 获取用户 uid
     * @param token 用户TOKEN
     * @return 从TOKEN中获取的用户ID
     */
    static long decodeToken(String token) {
        try {
            return Long.valueOf(token.substring(17), 16);
        } catch (Exception ignored) {}
        return 0;
    }
}
