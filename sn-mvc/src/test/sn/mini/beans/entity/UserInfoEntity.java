/**
 * Created the com.mengyi.user.auth.beans.entity.UserInfoEntity.java
 * @created 2017-08-02 09:35:47
 */
package test.sn.mini.beans.entity;

import java.util.Date;

import sn.mini.dao.annotaion.Binding;

/**
 * com.mengyi.user.auth.beans.entity.UserInfoEntity.java
 * @author XChao
 */
@Binding("userauth_user_info")
public class UserInfoEntity {
	public static final String TABLE_NAME = "users_user_info";

	public static final String USER_ID = "user_id";

	public static final String USER_NAME = "user_name";

	public static final String USER_NICK = "user_nick";

	public static final String USER_REAL_NAME = "user_real_name";

	public static final String USER_PASSWORD = "user_password";

	public static final String USER_SEED = "user_seed";

	public static final String USER_HEAD = "user_head";

	public static final String USER_EMAIL = "user_email";

	public static final String USER_PHONE = "user_phone";

	public static final String USER_IDCARD = "user_idcard";

	public static final String USER_GENDER = "user_gender";

	public static final String USER_BIRTHDAY = "user_birthday";

	public static final String USER_ADDRESS = "user_address";

	public static final String USER_SIGNATURE = "user_signature";

	public static final String USER_REGION_ID = "user_region_id";

	public static final String USER_SCHOOL = "user_school";

	public static final String USER_STATUS = "user_status";

	public static final String USER_VERSION = "user_version";

	public static final String USER_TOKEN = "user_token";

	public static final String USER_KEYS = "user_keys";

	public static final String USER_UPDATE = "user_update";

	public static final String USER_CREATE = "user_create";

	private long id; //

	private String name; //

	private String nick; //

	private String realName; //

	private String password; //

	private String seed; //

	private String head; //

	private String email; //

	private String phone; //

	private String idcard; //

	private int gender; //

	private Date birthday; //

	private String address; //

	private String signature; //

	private long regionId; //

	private String school; //

	private int status; //

	private String version;

	private String token; //

	private String keys; //

	private Date update; //

	private Date create; //

	/**
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	@Binding("user_id")
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	@Binding("user_name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return this.nick;
	}

	/**
	 * @param nick the nick to set
	 */
	@Binding("user_nick")
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return this.realName;
	}

	/**
	 * @param realName the realName to set
	 */
	@Binding("user_real_name")
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	@Binding("user_password")
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the seed
	 */
	public String getSeed() {
		return this.seed;
	}

	/**
	 * @param seed the seed to set
	 */
	@Binding("user_seed")
	public void setSeed(String seed) {
		this.seed = seed;
	}

	/**
	 * @return the head
	 */
	public String getHead() {
		return this.head;
	}

	/**
	 * @param head the head to set
	 */
	@Binding("user_head")
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	@Binding("user_email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * @param phone the phone to set
	 */
	@Binding("user_phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the idcard
	 */
	public String getIdcard() {
		return this.idcard;
	}

	/**
	 * @param idcard the idcard to set
	 */
	@Binding("user_idcard")
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	/**
	 * @return the gender
	 */
	public int getGender() {
		return this.gender;
	}

	/**
	 * @param gender the gender to set
	 */
	@Binding("user_gender")
	public void setGender(int gender) {
		this.gender = gender;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return this.birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	@Binding("user_birthday")
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param address the address to set
	 */
	@Binding("user_address")
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return this.signature;
	}

	/**
	 * @param signature the signature to set
	 */
	@Binding("user_signature")
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @return the regionId
	 */
	public long getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	@Binding("user_region_id")
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return this.school;
	}

	/**
	 * @param school the school to set
	 */
	@Binding("user_school")
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * @param status the status to set
	 */
	@Binding("user_status")
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	@Binding("user_version")
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * @param token the token to set
	 */
	@Binding("user_token")
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the keys
	 */
	public String getKeys() {
		return this.keys;
	}

	/**
	 * @param keys the keys to set
	 */
	@Binding("user_keys")
	public void setKeys(String keys) {
		this.keys = keys;
	}

	/**
	 * @return the update
	 */
	public Date getUpdate() {
		return this.update;
	}

	/**
	 * @param update the update to set
	 */
	@Binding("user_update")
	public void setUpdate(Date update) {
		this.update = update;
	}

	/**
	 * @return the create
	 */
	public Date getCreate() {
		return this.create;
	}

	/**
	 * @param create the create to set
	 */
	@Binding("user_create")
	public void setCreate(Date create) {
		this.create = create;
	}
}
