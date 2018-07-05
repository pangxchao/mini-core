package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
@Table("user_info")
public class UserInfo implements IDaoModel<UserInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : user_info */
	public static final String TABLE_NAME = "user_info";
	/** 用户ID:  user_id */
	public static final String USER_ID = "user_id";
	/** 用户名:  user_name */
	public static final String USER_NAME = "user_name";
	/** 用户手机号:  user_phone */
	public static final String USER_PHONE = "user_phone";
	/** 手机号是否认证:  user_phone_auth */
	public static final String USER_PHONE_AUTH = "user_phone_auth";
	/** 用户真实姓名:  user_real_name */
	public static final String USER_REAL_NAME = "user_real_name";
	/** 用户密码:  user_password */
	public static final String USER_PASSWORD = "user_password";
	/** 密码种子:  user_seed */
	public static final String USER_SEED = "user_seed";
	/** 邮箱:  user_email */
	public static final String USER_EMAIL = "user_email";
	/** 邮箱是否认证:  user_email_auth */
	public static final String USER_EMAIL_AUTH = "user_email_auth";
	/** 用户头像所在云节点ID:  user_cloud_id */
	public static final String USER_CLOUD_ID = "user_cloud_id";
	/** 用户头像URL:  user_head_url */
	public static final String USER_HEAD_URL = "user_head_url";
	/** 用户所在学校名称:  user_school */
	public static final String USER_SCHOOL = "user_school";
	/** 用户地区ID:  user_region_id */
	public static final String USER_REGION_ID = "user_region_id";
	/** 用户是否被锁定:  user_is_lock */
	public static final String USER_IS_LOCK = "user_is_lock";
	/** 用户是否被禁用:  user_is_disable */
	public static final String USER_IS_DISABLE = "user_is_disable";
	/** 用户是否被删除:  user_is_delete */
	public static final String USER_IS_DELETE = "user_is_delete";
	/** 1-支持消息推送; 0-不支持:  user_push_service */
	public static final String USER_PUSH_SERVICE = "user_push_service";
	/** 1-有声音提醒;0-无声音提醒:  user_has_sound */
	public static final String USER_HAS_SOUND = "user_has_sound";
	/** 1-有振动提醒;0-无振动提醒:  user_has_vibration */
	public static final String USER_HAS_VIBRATION = "user_has_vibration";
	/** 1-验证消息免打扰:  user_validate_un_disturb */
	public static final String USER_VALIDATE_UN_DISTURB = "user_validate_un_disturb";
	/** 1-我的消息免打扰:  user_my_un_disturb */
	public static final String USER_MY_UN_DISTURB = "user_my_un_disturb";
	/** 用户登录TOKEN:  user_token */
	public static final String USER_TOKEN = "user_token";
	/** 用户设备ID:  user_device_id */
	public static final String USER_DEVICE_ID = "user_device_id";
	/** 用户设备类型： 0-未知  1-iOS   2-Android:  user_device_type */
	public static final String USER_DEVICE_TYPE = "user_device_type";
	/** 用户最后登录的制作工具版本号:  user_version */
	public static final String USER_VERSION = "user_version";
	/** 用户签名:  user_signature */
	public static final String USER_SIGNATURE = "user_signature";
	/** 用户日昵称:  user_nick */
	public static final String USER_NICK = "user_nick";
	/** 最后登录时间:  user_login_time */
	public static final String USER_LOGIN_TIME = "user_login_time";
	/** 注册时间:  user_register_time */
	public static final String USER_REGISTER_TIME = "user_register_time";
	/** 用户ID */
	private long id;
	/** 用户名 */
	private String name;
	/** 用户手机号 */
	private String phone;
	/** 手机号是否认证 */
	private int phoneAuth;
	/** 用户真实姓名 */
	private String realName;
	/** 用户密码 */
	private String password;
	/** 密码种子 */
	private String seed;
	/** 邮箱 */
	private String email;
	/** 邮箱是否认证 */
	private int emailAuth;
	/** 用户头像所在云节点ID */
	private long cloudId;
	/** 用户头像URL */
	private String headUrl;
	/** 用户所在学校名称 */
	private String school;
	/** 用户地区ID */
	private long regionId;
	/** 用户是否被锁定 */
	private int isLock;
	/** 用户是否被禁用 */
	private int isDisable;
	/** 用户是否被删除 */
	private int isDelete;
	/** 1-支持消息推送; 0-不支持 */
	private int pushService;
	/** 1-有声音提醒;0-无声音提醒 */
	private int hasSound;
	/** 1-有振动提醒;0-无振动提醒 */
	private int hasVibration;
	/** 1-验证消息免打扰 */
	private int validateUnDisturb;
	/** 1-我的消息免打扰 */
	private int myUnDisturb;
	/** 用户登录TOKEN */
	private String token;
	/** 用户设备ID */
	private String deviceId;
	/** 用户设备类型： 0-未知  1-iOS   2-Android */
	private int deviceType;
	/** 用户最后登录的制作工具版本号 */
	private String version;
	/** 用户签名 */
	private String signature;
	/** 用户日昵称 */
	private String nick;
	/** 最后登录时间 */
	private long loginTime;
	/** 注册时间 */
	private long registerTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="user_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getName() { 
		return this.name;
	} 
	@Column(value="user_name")
	public void setName(String name) { 
		this.name = name;
	} 
	public String getPhone() { 
		return this.phone;
	} 
	@Column(value="user_phone")
	public void setPhone(String phone) { 
		this.phone = phone;
	} 
	public int getPhoneAuth() { 
		return this.phoneAuth;
	} 
	@Column(value="user_phone_auth")
	public void setPhoneAuth(int phoneAuth) { 
		this.phoneAuth = phoneAuth;
	} 
	public String getRealName() { 
		return this.realName;
	} 
	@Column(value="user_real_name")
	public void setRealName(String realName) { 
		this.realName = realName;
	} 
	public String getPassword() { 
		return this.password;
	} 
	@Column(value="user_password")
	public void setPassword(String password) { 
		this.password = password;
	} 
	public String getSeed() { 
		return this.seed;
	} 
	@Column(value="user_seed")
	public void setSeed(String seed) { 
		this.seed = seed;
	} 
	public String getEmail() { 
		return this.email;
	} 
	@Column(value="user_email")
	public void setEmail(String email) { 
		this.email = email;
	} 
	public int getEmailAuth() { 
		return this.emailAuth;
	} 
	@Column(value="user_email_auth")
	public void setEmailAuth(int emailAuth) { 
		this.emailAuth = emailAuth;
	} 
	public long getCloudId() { 
		return this.cloudId;
	} 
	@Column(value="user_cloud_id")
	public void setCloudId(long cloudId) { 
		this.cloudId = cloudId;
	} 
	public String getHeadUrl() { 
		return this.headUrl;
	} 
	@Column(value="user_head_url")
	public void setHeadUrl(String headUrl) { 
		this.headUrl = headUrl;
	} 
	public String getSchool() { 
		return this.school;
	} 
	@Column(value="user_school")
	public void setSchool(String school) { 
		this.school = school;
	} 
	public long getRegionId() { 
		return this.regionId;
	} 
	@Column(value="user_region_id")
	public void setRegionId(long regionId) { 
		this.regionId = regionId;
	} 
	public int getIsLock() { 
		return this.isLock;
	} 
	@Column(value="user_is_lock")
	public void setIsLock(int isLock) { 
		this.isLock = isLock;
	} 
	public int getIsDisable() { 
		return this.isDisable;
	} 
	@Column(value="user_is_disable")
	public void setIsDisable(int isDisable) { 
		this.isDisable = isDisable;
	} 
	public int getIsDelete() { 
		return this.isDelete;
	} 
	@Column(value="user_is_delete")
	public void setIsDelete(int isDelete) { 
		this.isDelete = isDelete;
	} 
	public int getPushService() { 
		return this.pushService;
	} 
	@Column(value="user_push_service")
	public void setPushService(int pushService) { 
		this.pushService = pushService;
	} 
	public int getHasSound() { 
		return this.hasSound;
	} 
	@Column(value="user_has_sound")
	public void setHasSound(int hasSound) { 
		this.hasSound = hasSound;
	} 
	public int getHasVibration() { 
		return this.hasVibration;
	} 
	@Column(value="user_has_vibration")
	public void setHasVibration(int hasVibration) { 
		this.hasVibration = hasVibration;
	} 
	public int getValidateUnDisturb() { 
		return this.validateUnDisturb;
	} 
	@Column(value="user_validate_un_disturb")
	public void setValidateUnDisturb(int validateUnDisturb) { 
		this.validateUnDisturb = validateUnDisturb;
	} 
	public int getMyUnDisturb() { 
		return this.myUnDisturb;
	} 
	@Column(value="user_my_un_disturb")
	public void setMyUnDisturb(int myUnDisturb) { 
		this.myUnDisturb = myUnDisturb;
	} 
	public String getToken() { 
		return this.token;
	} 
	@Column(value="user_token")
	public void setToken(String token) { 
		this.token = token;
	} 
	public String getDeviceId() { 
		return this.deviceId;
	} 
	@Column(value="user_device_id")
	public void setDeviceId(String deviceId) { 
		this.deviceId = deviceId;
	} 
	public int getDeviceType() { 
		return this.deviceType;
	} 
	@Column(value="user_device_type")
	public void setDeviceType(int deviceType) { 
		this.deviceType = deviceType;
	} 
	public String getVersion() { 
		return this.version;
	} 
	@Column(value="user_version")
	public void setVersion(String version) { 
		this.version = version;
	} 
	public String getSignature() { 
		return this.signature;
	} 
	@Column(value="user_signature")
	public void setSignature(String signature) { 
		this.signature = signature;
	} 
	public String getNick() { 
		return this.nick;
	} 
	@Column(value="user_nick")
	public void setNick(String nick) { 
		this.nick = nick;
	} 
	public long getLoginTime() { 
		return this.loginTime;
	} 
	@Column(value="user_login_time")
	public void setLoginTime(long loginTime) { 
		this.loginTime = loginTime;
	} 
	public long getRegisterTime() { 
		return this.registerTime;
	} 
	@Column(value="user_register_time")
	public void setRegisterTime(long registerTime) { 
		this.registerTime = registerTime;
	} 
	public static int insert(IDao dao, long id, String name, String phone, int phoneAuth, String realName, String password, String seed, String email, int emailAuth, long cloudId, String headUrl, String school, long regionId, int isLock, int isDisable, int isDelete, int pushService, int hasSound, int hasVibration, int validateUnDisturb, int myUnDisturb, String token, String deviceId, int deviceType, String version, String signature, String nick, long loginTime, long registerTime) {
		return dao.insert(TABLE_NAME, new String[]{USER_ID, USER_NAME, USER_PHONE, USER_PHONE_AUTH, USER_REAL_NAME, USER_PASSWORD, USER_SEED, USER_EMAIL, USER_EMAIL_AUTH, USER_CLOUD_ID, USER_HEAD_URL, USER_SCHOOL, USER_REGION_ID, USER_IS_LOCK, USER_IS_DISABLE, USER_IS_DELETE, USER_PUSH_SERVICE, USER_HAS_SOUND, USER_HAS_VIBRATION, USER_VALIDATE_UN_DISTURB, USER_MY_UN_DISTURB, USER_TOKEN, USER_DEVICE_ID, USER_DEVICE_TYPE, USER_VERSION, USER_SIGNATURE, USER_NICK, USER_LOGIN_TIME, USER_REGISTER_TIME}, id, name, phone, phoneAuth, realName, password, seed, email, emailAuth, cloudId, headUrl, school, regionId, isLock, isDisable, isDelete, pushService, hasSound, hasVibration, validateUnDisturb, myUnDisturb, token, deviceId, deviceType, version, signature, nick, loginTime, registerTime);
	}
	

	public static int updateById(IDao dao, long id, String name, String phone, int phoneAuth, String realName, String password, String seed, String email, int emailAuth, long cloudId, String headUrl, String school, long regionId, int isLock, int isDisable, int isDelete, int pushService, int hasSound, int hasVibration, int validateUnDisturb, int myUnDisturb, String token, String deviceId, int deviceType, String version, String signature, String nick, long loginTime, long registerTime) {
		return dao.update(TABLE_NAME, new String[]{USER_ID, USER_NAME, USER_PHONE, USER_PHONE_AUTH, USER_REAL_NAME, USER_PASSWORD, USER_SEED, USER_EMAIL, USER_EMAIL_AUTH, USER_CLOUD_ID, USER_HEAD_URL, USER_SCHOOL, USER_REGION_ID, USER_IS_LOCK, USER_IS_DISABLE, USER_IS_DELETE, USER_PUSH_SERVICE, USER_HAS_SOUND, USER_HAS_VIBRATION, USER_VALIDATE_UN_DISTURB, USER_MY_UN_DISTURB, USER_TOKEN, USER_DEVICE_ID, USER_DEVICE_TYPE, USER_VERSION, USER_SIGNATURE, USER_NICK, USER_LOGIN_TIME, USER_REGISTER_TIME}, new String[]{USER_ID}, id, name, phone, phoneAuth, realName, password, seed, email, emailAuth, cloudId, headUrl, school, regionId, isLock, isDisable, isDelete, pushService, hasSound, hasVibration, validateUnDisturb, myUnDisturb, token, deviceId, deviceType, version, signature, nick, loginTime, registerTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{USER_ID}, id);
	}
	

	public static UserInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, USER_ID, USER_NAME, USER_PHONE, USER_PHONE_AUTH, USER_REAL_NAME, USER_PASSWORD, USER_SEED, USER_EMAIL, USER_EMAIL_AUTH, USER_CLOUD_ID, USER_HEAD_URL, USER_SCHOOL, USER_REGION_ID, USER_IS_LOCK, USER_IS_DISABLE, USER_IS_DELETE, USER_PUSH_SERVICE, USER_HAS_SOUND, USER_HAS_VIBRATION, USER_VALIDATE_UN_DISTURB, USER_MY_UN_DISTURB, USER_TOKEN, USER_DEVICE_ID, USER_DEVICE_TYPE, USER_VERSION, USER_SIGNATURE, USER_NICK, USER_LOGIN_TIME, USER_REGISTER_TIME).whereTrue();
		sql.andEq(USER_ID).params(id);
		return dao.queryOne(UserInfo.class, sql);
	}
	

	public static List<UserInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, USER_ID, USER_NAME, USER_PHONE, USER_PHONE_AUTH, USER_REAL_NAME, USER_PASSWORD, USER_SEED, USER_EMAIL, USER_EMAIL_AUTH, USER_CLOUD_ID, USER_HEAD_URL, USER_SCHOOL, USER_REGION_ID, USER_IS_LOCK, USER_IS_DISABLE, USER_IS_DELETE, USER_PUSH_SERVICE, USER_HAS_SOUND, USER_HAS_VIBRATION, USER_VALIDATE_UN_DISTURB, USER_MY_UN_DISTURB, USER_TOKEN, USER_DEVICE_ID, USER_DEVICE_TYPE, USER_VERSION, USER_SIGNATURE, USER_NICK, USER_LOGIN_TIME, USER_REGISTER_TIME).whereTrue();
		return dao.query(UserInfo.class, sql);
	}
	

	public static List<UserInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, USER_ID, USER_NAME, USER_PHONE, USER_PHONE_AUTH, USER_REAL_NAME, USER_PASSWORD, USER_SEED, USER_EMAIL, USER_EMAIL_AUTH, USER_CLOUD_ID, USER_HEAD_URL, USER_SCHOOL, USER_REGION_ID, USER_IS_LOCK, USER_IS_DISABLE, USER_IS_DELETE, USER_PUSH_SERVICE, USER_HAS_SOUND, USER_HAS_VIBRATION, USER_VALIDATE_UN_DISTURB, USER_MY_UN_DISTURB, USER_TOKEN, USER_DEVICE_ID, USER_DEVICE_TYPE, USER_VERSION, USER_SIGNATURE, USER_NICK, USER_LOGIN_TIME, USER_REGISTER_TIME).whereTrue();
		return dao.query(paging, UserInfo.class, sql);
	}
	

} 