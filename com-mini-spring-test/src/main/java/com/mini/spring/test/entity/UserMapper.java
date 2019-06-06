package com.mini.spring.test.entity;

import com.mini.util.dao.IMapper;
import com.mini.util.dao.sql.SQLSelect;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mini.spring.test.entity.User.*;

@Repository("userMapper")
public class UserMapper implements IMapper<User> {

    @Override
    public User execute(ResultSet rs, int number) throws SQLException {
        User user = new User();
        // 用户ID
        user.setId(rs.getLong(ID));
        // 用户名
        user.setName(rs.getString(NAME));
        // 用户手机号
        user.setPhone(rs.getString(PHONE));
        // 1- 手机号已认证
        user.setPhoneAuth(rs.getInt(PHONE_AUTH));
        // 用户姓名
        user.setRealName(rs.getString(REAL_NAME));
        // 用户密码
        user.setPassword(rs.getString(PASSWORD));
        // 密码种子
        user.setSeed(rs.getString(SEED));
        // 邮箱
        user.setEmail(rs.getString(EMAIL));
        // 1-邮箱已认证
        user.setEmailAuth(rs.getInt(EMAIL_AUTH));
        // 头像所属云节点
        user.setCloudId(rs.getLong(CLOUD_ID));
        // 头像URL
        user.setHeadUrl(rs.getString(HEAD_URL));
        // 用户单位名称
        user.setUnit(rs.getString(UNIT));
        // 用户所属地区ID
        user.setRegionId(rs.getLong(REGION_ID));
        // 1-用户被锁定
        user.setLocked(rs.getInt(LOCKED));
        // 1-用户被禁用
        user.setDisable(rs.getInt(DISABLE));
        // 1-用户被删除
        user.setDelete(rs.getInt(DELETE));
        // 1-接收新消息通知
        user.setPushService(rs.getInt(PUSH_SERVICE));
        // 1-新消息通知有声音提示
        user.setHasSound(rs.getInt(HAS_SOUND));
        // 1-新消息通知有振动提示
        user.setHasVibration(rs.getInt(HAS_VIBRATION));
        // 1-验证消息免打扰
        user.setValidateUnDisturb(rs.getInt(VALIDATE_UN_DISTURB));
        // 1-我的消息免打扰
        user.setMyUnDisturb(rs.getInt(MY_UN_DISTURB));
        // 1-用户在线购买过VIP
        user.setOnline(rs.getInt(ONLINE));
        // 1-用户线下购买过VIP
        user.setOffline(rs.getInt(OFFLINE));
        // 1-系统赠送过用户VIP
        user.setGive(rs.getInt(GIVE));
        // 1-用户VIP最后到期时间
        user.setPay(rs.getTimestamp(PAY));
        // 1-用户积分数量
        user.setScore(rs.getInt(SCORE));
        // 乐观锁字段，并发时的用户保护
        user.setLock(rs.getLong(LOCK));
        // 最后登录时间
        user.setLoginTime(rs.getTimestamp(LOGIN_TIME));
        // 注册时间
        user.setRegisterTime(rs.getTimestamp(REGISTER_TIME));
        return user;
    }

    public static SQLSelect sql() {
        return new SQLSelect()
                // 表名称
                .from(TABLE)
                // 用户ID
                .keys(ID)
                // 用户名
                .keys(NAME)
                // 用户手机号
                .keys(PHONE)
                // 1-手机号已认证
                .keys(PHONE_AUTH)
                // 用户姓名
                .keys(REAL_NAME)
                // 密码
                .keys(PASSWORD)
                // 密码种子
                .keys(SEED)
                // 邮箱
                .keys(EMAIL)
                // 1-邮箱已认证
                .keys(EMAIL_AUTH)
                // 头像所在云节点ID
                .keys(CLOUD_ID)
                // 头像URL
                .keys(HEAD_URL)
                // 单位名称
                .keys(UNIT)
                // 所属地区ID
                .keys(REGION_ID)
                // 1-已锁定
                .keys(LOCKED)
                // 1- 已禁用
                .keys(DISABLE)
                // 1-已删除
                .keys(DELETE)
                // 1-接收新消息通知
                .keys(PUSH_SERVICE)
                // 1-新消息通知有声音提示
                .keys(HAS_SOUND)
                // 1-新消息有振动提示
                .keys(HAS_VIBRATION)
                // 1-验证消息免打扰
                .keys(VALIDATE_UN_DISTURB)
                // 1-我的消息免打扰
                .keys(MY_UN_DISTURB)
                // 1-在线购买过VIP
                .keys(ONLINE)
                // 1-线下购买过VIP
                .keys(OFFLINE)
                // 1-系统赠送过VIP
                .keys(GIVE)
                // 用户VIP到期时间
                .keys(PAY)
                // 用户积分
                .keys(SCORE)
                // 锁字段
                .keys(LOCK)
                // 最后登录时间
                .keys(LOGIN_TIME)
                // 最后注册时间
                .keys(REGISTER_TIME);
    }
}
