package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("user_card_info")
public class UserCardInfo implements IDaoModel<UserCardInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : user_card_info */
	public static final String TABLE_NAME = "user_card_info";
	/** 卡片ID:  card_id */
	public static final String CARD_ID = "card_id";
	/** 卡号:  card_number */
	public static final String CARD_NUMBER = "card_number";
	/** 密码:  card_password */
	public static final String CARD_PASSWORD = "card_password";
	/** 密码种子:  card_password_seed */
	public static final String CARD_PASSWORD_SEED = "card_password_seed";
	/** 有效期/时长(单位月):  card_useful_time */
	public static final String CARD_USEFUL_TIME = "card_useful_time";
	/** 卡片是否被使用(使用卡片信息不能被修改):  card_is_used */
	public static final String CARD_IS_USED = "card_is_used";
	/** 使用卡片的用户ID:  card_user_id */
	public static final String CARD_USER_ID = "card_user_id";
	/** 卡片的使用时间:  card_use_time */
	public static final String CARD_USE_TIME = "card_use_time";
	/** 卡片信息最后修改时间:  card_update_time */
	public static final String CARD_UPDATE_TIME = "card_update_time";
	/** 卡片ID */
	private long id;
	/** 卡号 */
	private String number;
	/** 密码 */
	private String password;
	/** 密码种子 */
	private String passwordSeed;
	/** 有效期/时长(单位月) */
	private int usefulTime;
	/** 卡片是否被使用(使用卡片信息不能被修改) */
	private int isUsed;
	/** 使用卡片的用户ID */
	private long userId;
	/** 卡片的使用时间 */
	private Date useTime;
	/** 卡片信息最后修改时间 */
	private Date updateTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="card_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getNumber() { 
		return this.number;
	} 
	@Column(value="card_number")
	public void setNumber(String number) { 
		this.number = number;
	} 
	public String getPassword() { 
		return this.password;
	} 
	@Column(value="card_password")
	public void setPassword(String password) { 
		this.password = password;
	} 
	public String getPasswordSeed() { 
		return this.passwordSeed;
	} 
	@Column(value="card_password_seed")
	public void setPasswordSeed(String passwordSeed) { 
		this.passwordSeed = passwordSeed;
	} 
	public int getUsefulTime() { 
		return this.usefulTime;
	} 
	@Column(value="card_useful_time")
	public void setUsefulTime(int usefulTime) { 
		this.usefulTime = usefulTime;
	} 
	public int getIsUsed() { 
		return this.isUsed;
	} 
	@Column(value="card_is_used")
	public void setIsUsed(int isUsed) { 
		this.isUsed = isUsed;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="card_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public Date getUseTime() { 
		return this.useTime;
	} 
	@Column(value="card_use_time")
	public void setUseTime(Date useTime) { 
		this.useTime = useTime;
	} 
	public Date getUpdateTime() { 
		return this.updateTime;
	} 
	@Column(value="card_update_time")
	public void setUpdateTime(Date updateTime) { 
		this.updateTime = updateTime;
	} 
	public static int insert(IDao dao, long id, String number, String password, String passwordSeed, int usefulTime, int isUsed, long userId, Date useTime, Date updateTime) {
		return dao.insert(TABLE_NAME, new String[]{CARD_ID, CARD_NUMBER, CARD_PASSWORD, CARD_PASSWORD_SEED, CARD_USEFUL_TIME, CARD_IS_USED, CARD_USER_ID, CARD_USE_TIME, CARD_UPDATE_TIME}, id, number, password, passwordSeed, usefulTime, isUsed, userId, useTime, updateTime);
	}
	

	public static int updateById(IDao dao, long id, String number, String password, String passwordSeed, int usefulTime, int isUsed, long userId, Date useTime, Date updateTime) {
		return dao.update(TABLE_NAME, new String[]{CARD_ID, CARD_NUMBER, CARD_PASSWORD, CARD_PASSWORD_SEED, CARD_USEFUL_TIME, CARD_IS_USED, CARD_USER_ID, CARD_USE_TIME, CARD_UPDATE_TIME}, new String[]{CARD_ID}, id, number, password, passwordSeed, usefulTime, isUsed, userId, useTime, updateTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{CARD_ID}, id);
	}
	

	public static UserCardInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, CARD_ID, CARD_NUMBER, CARD_PASSWORD, CARD_PASSWORD_SEED, CARD_USEFUL_TIME, CARD_IS_USED, CARD_USER_ID, CARD_USE_TIME, CARD_UPDATE_TIME).whereTrue();
		sql.andEq(CARD_ID).params(id);
		return dao.queryOne(UserCardInfo.class, sql);
	}
	

	public static List<UserCardInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, CARD_ID, CARD_NUMBER, CARD_PASSWORD, CARD_PASSWORD_SEED, CARD_USEFUL_TIME, CARD_IS_USED, CARD_USER_ID, CARD_USE_TIME, CARD_UPDATE_TIME).whereTrue();
		return dao.query(UserCardInfo.class, sql);
	}
	

	public static List<UserCardInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, CARD_ID, CARD_NUMBER, CARD_PASSWORD, CARD_PASSWORD_SEED, CARD_USEFUL_TIME, CARD_IS_USED, CARD_USER_ID, CARD_USE_TIME, CARD_UPDATE_TIME).whereTrue();
		return dao.query(paging, UserCardInfo.class, sql);
	}
	

} 