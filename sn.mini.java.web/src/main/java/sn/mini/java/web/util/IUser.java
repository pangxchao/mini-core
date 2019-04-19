package sn.mini.java.web.util;

import sn.mini.java.util.PKGenerator;

/**
 * com.xcc.web.entity.IUser.java
 * @author XChao
 */
public interface IUser {
    String USER_KEY = "SN_USER_SESSION_KEY";

    /**
     * 重新创建用户token
     * @param uid 用户ID
     * @return 根据用户ID生成的用户TOKEN
     */
    static String newToken(long uid) {
        return PKGenerator.genseed(17) + Long.toHexString(uid).toUpperCase();
    }

    /**
     * *解码token 获取用户 uid
     * @param token 用户TOKEN
     * @return 从TOKEN中获取的用户ID
     */
    static long decodeToken(String token) {
        try {
            return Long.valueOf(token.substring(17), 16);
        } catch (Exception ignored) {}
        return 0;
    }

    /**
     * 获取用户ID
     * @return the id
     */
    long getId();

    /**
     * 设置用户ID
     * @param id the id to set
     */
    void setId(long id);
}
