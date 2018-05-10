/**
 * Created the com.cfinal.web.entity.CFUser.java
 *
 * @created 2016年9月29日 上午10:56:39
 * @version 1.0.0
 */
package sn.mini.java.web.util;

import sn.mini.java.util.PKGenerator;
import sn.mini.java.util.digest.Base64Util;
import sn.mini.java.util.logger.Log;

/**
 * com.xcc.web.entity.IUser.java
 *
 * @author XChao
 */
public interface IUser {
     String USER_KEY = "SN_USER_SESSION_KEY";

    /**
     * @return the id
     */
     long getId();

    /**
     * @param id the id to set
     */
     void setId(long id);

    /**
     * 重新创建用户token
     *
     * @param uid
     * @return
     */
     static String newToken(long uid) {
        return Base64Util.encode(PKGenerator.genseed(6) + uid);
    }

    /**
     * 解码token 获取用户uid
     *
     * @param token
     * @return
     */
     static long decodeToken(String token) {
        try {
            return Long.valueOf(Base64Util.decode(token).substring(6));
        } catch (Exception e) {
            Log.error("IUser.decodeToken error.");
        }
        return 0;
    }
}
