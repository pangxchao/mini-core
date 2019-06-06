package com.mini.spring.test.entity;

import com.mini.spring.util.IUser;

import java.sql.Timestamp;

/**
 * 用户信息表
 * @author xchao
 */
public class User implements IUser {
    private static final long serialVersionUID = -8691133019477168146L;

    // 表名
    public static final String TABLE = "user_info";
    // 用户ID
    public static final String ID = "user_id";
    // 用户名
    public static final String NAME = "user_name";
    // 手机号
    public static final String PHONE = "user_phone";
    // 1-手机号已认证
    public static final String PHONE_AUTH = "user_phone_auth";
    // 姓名
    public static final String REAL_NAME = "user_real_name";
    // 密码
    public static final String PASSWORD = "user_password";
    // 种子
    public static final String SEED = "user_seed";
    // 邮箱
    public static final String EMAIL = "user_email";
    // 1-邮箱已认证
    public static final String EMAIL_AUTH = "user_email_auth";
    // 用户头像所有云
    public static final String CLOUD_ID = "user_cloud_id";
    // 用户头像ID
    public static final String HEAD_URL = "user_head_url";
    // 用户所单位名称
    public static final String UNIT = "user_unit";
    // 用户所属地区
    public static final String REGION_ID = "user_region_id";
    // 1-用户已被锁定
    public static final String LOCKED = "user_locked";
    // 1-用户已被禁用
    public static final String DISABLE = "user_disable";
    // 1-用户已删除
    public static final String DELETE = "user_delete";
    // 1-用户接收消息通知
    public static final String PUSH_SERVICE = "user_push_service";
    // 1-用户接收消息通知时有声音提示
    public static final String HAS_SOUND = "user_has_sound";
    // 1-用户接收消息通知时有振动提示
    public static final String HAS_VIBRATION = "user_has_vibration";
    // 1-验证消息免打扰
    public static final String VALIDATE_UN_DISTURB = "user_validate_un_disturb";
    // 1-我的消息免打扰
    public static final String MY_UN_DISTURB = "user_my_un_disturb";
    // 1-用户在线购买过VIP
    public static final String ONLINE = "user_online";
    // 1-用户离线购买过VIP
    public static final String OFFLINE = "user_offline";
    // 1-系统赠送过用户VIP
    public static final String GIVE = "user_give";
    // 1-系统赠送过用户VIP
    public static final String PAY = "user_pay";
    // 1-系统赠送过用户VIP
    public static final String SCORE = "user_score";
    // 1-系统赠送过用户VIP
    public static final String LOCK = "user_lock";
    // 最后登录时间
    public static final String LOGIN_TIME = "user_login_time";
    // 用户注册时间
    public static final String REGISTER_TIME = "user_register_time";

    // 用户ID
    private long id;
    // 用户名
    private String name;
    // 手机号
    private String phone;
    // 1-手机号已认证
    private int phoneAuth;
    // 姓名
    private String realName;
    // 密码
    private String password;
    // 密码种子
    private String seed;
    // 邮箱
    private String email;
    // 1-邮箱已认证
    private int emailAuth;
    // 头像所在云节点
    private long cloudId;
    // 头像地址
    private String headUrl;
    // 所属单位
    private String unit;
    // 所属地区
    private long regionId;
    // 1-用户被锁定
    private int locked;
    // 1-用户被禁用
    private int disable;
    // 1-用户被删除
    private int delete;
    // 1-接收消息推送
    private int pushService;
    // 1-新消息有声音提示
    private int hasSound;
    // 1-新消息有震动提示
    private int hasVibration;
    // 1-验证消息免打扰
    private int validateUnDisturb;
    // 1-我的消息免打扰
    private int myUnDisturb;
    // 1-在线购买用户
    private int online;
    // 1-线下购买用户
    private int offline;
    // 1-系统赠送过VIP
    private int give;
    // 个人VIP到期时间
    private Timestamp pay;
    // 用户积分数
    private int score;
    // 用户锁
    private long lock;
    // 最后登录时间
    private Timestamp loginTime;
    // 注册时间
    private Timestamp registerTime;

    @Override
    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getPhoneAuth() {
        return phoneAuth;
    }

    public User setPhoneAuth(int phoneAuth) {
        this.phoneAuth = phoneAuth;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSeed() {
        return seed;
    }

    public User setSeed(String seed) {
        this.seed = seed;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getEmailAuth() {
        return emailAuth;
    }

    public User setEmailAuth(int emailAuth) {
        this.emailAuth = emailAuth;
        return this;
    }

    public long getCloudId() {
        return cloudId;
    }

    public User setCloudId(long cloudId) {
        this.cloudId = cloudId;
        return this;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public User setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public User setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public long getRegionId() {
        return regionId;
    }

    public User setRegionId(long regionId) {
        this.regionId = regionId;
        return this;
    }

    public int getLocked() {
        return locked;
    }

    public User setLocked(int locked) {
        this.locked = locked;
        return this;
    }

    public int getDisable() {
        return disable;
    }

    public User setDisable(int disable) {
        this.disable = disable;
        return this;
    }

    public int getDelete() {
        return delete;
    }

    public User setDelete(int delete) {
        this.delete = delete;
        return this;
    }

    public int getPushService() {
        return pushService;
    }

    public User setPushService(int pushService) {
        this.pushService = pushService;
        return this;
    }

    public int getHasSound() {
        return hasSound;
    }

    public User setHasSound(int hasSound) {
        this.hasSound = hasSound;
        return this;
    }

    public int getHasVibration() {
        return hasVibration;
    }

    public User setHasVibration(int hasVibration) {
        this.hasVibration = hasVibration;
        return this;
    }

    public int getValidateUnDisturb() {
        return validateUnDisturb;
    }

    public User setValidateUnDisturb(int validateUnDisturb) {
        this.validateUnDisturb = validateUnDisturb;
        return this;
    }

    public int getMyUnDisturb() {
        return myUnDisturb;
    }

    public User setMyUnDisturb(int myUnDisturb) {
        this.myUnDisturb = myUnDisturb;
        return this;
    }

    public int getOnline() {
        return online;
    }

    public User setOnline(int online) {
        this.online = online;
        return this;
    }

    public int getOffline() {
        return offline;
    }

    public User setOffline(int offline) {
        this.offline = offline;
        return this;
    }

    public int getGive() {
        return give;
    }

    public User setGive(int give) {
        this.give = give;
        return this;
    }

    public Timestamp getPay() {
        return pay;
    }

    public User setPay(Timestamp pay) {
        this.pay = pay;
        return this;
    }

    public int getScore() {
        return score;
    }

    public User setScore(int score) {
        this.score = score;
        return this;
    }

    public long getLock() {
        return lock;
    }

    public User setLock(long lock) {
        this.lock = lock;
        return this;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public User setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public User setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
        return this;
    }
}
