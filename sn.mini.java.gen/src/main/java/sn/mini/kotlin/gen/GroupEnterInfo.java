package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("group_enter_info")
public class GroupEnterInfo implements IDaoModel<GroupEnterInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : group_enter_info */
	public static final String TABLE_NAME = "group_enter_info";
	/** 集团ID:  group_id */
	public static final String GROUP_ID = "group_id";
	/** 集团名称:  group_name */
	public static final String GROUP_NAME = "group_name";
	/** 集团地址:  group_address */
	public static final String GROUP_ADDRESS = "group_address";
	/** 集团邮编:  group_zipcode */
	public static final String GROUP_ZIPCODE = "group_zipcode";
	/** 集团联系人:  group_linkman */
	public static final String GROUP_LINKMAN = "group_linkman";
	/** 集团联系电话:  group_phone */
	public static final String GROUP_PHONE = "group_phone";
	/** 集团联系邮箱地址:  group_email */
	public static final String GROUP_EMAIL = "group_email";
	/** 集团联系人职位:  group_job */
	public static final String GROUP_JOB = "group_job";
	/** 集团购买用户数:  group_auth_num */
	public static final String GROUP_AUTH_NUM = "group_auth_num";
	/** 赠送用户数:  group_give_num */
	public static final String GROUP_GIVE_NUM = "group_give_num";
	/** 集团拥有者ID:  group_user_id */
	public static final String GROUP_USER_ID = "group_user_id";
	/** 购买时间:  group_buy_time */
	public static final String GROUP_BUY_TIME = "group_buy_time";
	/** 到期时间:  group_expired */
	public static final String GROUP_EXPIRED = "group_expired";
	/** 将VIP时间写入用户数据:  group_expired_type */
	public static final String GROUP_EXPIRED_TYPE = "group_expired_type";
	/** 集团专属云节点:  group_cloud_id */
	public static final String GROUP_CLOUD_ID = "group_cloud_id";
	/** 集团使用制作工具数量:  group_tool_num */
	public static final String GROUP_TOOL_NUM = "group_tool_num";
	/** 是否台允许加入:  group_is_join */
	public static final String GROUP_IS_JOIN = "group_is_join";
	/** 加入时是否需要验证:  group_join_verify */
	public static final String GROUP_JOIN_VERIFY = "group_join_verify";
	/** 集团承诺书附件地址:  group_promise */
	public static final String GROUP_PROMISE = "group_promise";
	/** 承诺书附件所有云节点ID:  group_promise_cloud_id */
	public static final String GROUP_PROMISE_CLOUD_ID = "group_promise_cloud_id";
	/** 状态, 0-未上传承诺书,1-已上传承诺书,2-承诺书已审核通过,3-承诺书审核未通过:  group_status */
	public static final String GROUP_STATUS = "group_status";
	/** 最后修改时间:  group_update_time */
	public static final String GROUP_UPDATE_TIME = "group_update_time";
	/** 集团ID */
	private long id;
	/** 集团名称 */
	private String name;
	/** 集团地址 */
	private String address;
	/** 集团邮编 */
	private String zipcode;
	/** 集团联系人 */
	private String linkman;
	/** 集团联系电话 */
	private String phone;
	/** 集团联系邮箱地址 */
	private String email;
	/** 集团联系人职位 */
	private String job;
	/** 集团购买用户数 */
	private int authNum;
	/** 赠送用户数 */
	private int giveNum;
	/** 集团拥有者ID */
	private long userId;
	/** 购买时间 */
	private long buyTime;
	/** 到期时间 */
	private long expired;
	/** 将VIP时间写入用户数据 */
	private int expiredType;
	/** 集团专属云节点 */
	private long cloudId;
	/** 集团使用制作工具数量 */
	private int toolNum;
	/** 是否台允许加入 */
	private int isJoin;
	/** 加入时是否需要验证 */
	private int joinVerify;
	/** 集团承诺书附件地址 */
	private String promise;
	/** 承诺书附件所有云节点ID */
	private long promiseCloudId;
	/** 状态, 0-未上传承诺书,1-已上传承诺书,2-承诺书已审核通过,3-承诺书审核未通过 */
	private int status;
	/** 最后修改时间 */
	private Date updateTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="group_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getName() { 
		return this.name;
	} 
	@Column(value="group_name")
	public void setName(String name) { 
		this.name = name;
	} 
	public String getAddress() { 
		return this.address;
	} 
	@Column(value="group_address")
	public void setAddress(String address) { 
		this.address = address;
	} 
	public String getZipcode() { 
		return this.zipcode;
	} 
	@Column(value="group_zipcode")
	public void setZipcode(String zipcode) { 
		this.zipcode = zipcode;
	} 
	public String getLinkman() { 
		return this.linkman;
	} 
	@Column(value="group_linkman")
	public void setLinkman(String linkman) { 
		this.linkman = linkman;
	} 
	public String getPhone() { 
		return this.phone;
	} 
	@Column(value="group_phone")
	public void setPhone(String phone) { 
		this.phone = phone;
	} 
	public String getEmail() { 
		return this.email;
	} 
	@Column(value="group_email")
	public void setEmail(String email) { 
		this.email = email;
	} 
	public String getJob() { 
		return this.job;
	} 
	@Column(value="group_job")
	public void setJob(String job) { 
		this.job = job;
	} 
	public int getAuthNum() { 
		return this.authNum;
	} 
	@Column(value="group_auth_num")
	public void setAuthNum(int authNum) { 
		this.authNum = authNum;
	} 
	public int getGiveNum() { 
		return this.giveNum;
	} 
	@Column(value="group_give_num")
	public void setGiveNum(int giveNum) { 
		this.giveNum = giveNum;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="group_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getBuyTime() { 
		return this.buyTime;
	} 
	@Column(value="group_buy_time")
	public void setBuyTime(long buyTime) { 
		this.buyTime = buyTime;
	} 
	public long getExpired() { 
		return this.expired;
	} 
	@Column(value="group_expired")
	public void setExpired(long expired) { 
		this.expired = expired;
	} 
	public int getExpiredType() { 
		return this.expiredType;
	} 
	@Column(value="group_expired_type")
	public void setExpiredType(int expiredType) { 
		this.expiredType = expiredType;
	} 
	public long getCloudId() { 
		return this.cloudId;
	} 
	@Column(value="group_cloud_id")
	public void setCloudId(long cloudId) { 
		this.cloudId = cloudId;
	} 
	public int getToolNum() { 
		return this.toolNum;
	} 
	@Column(value="group_tool_num")
	public void setToolNum(int toolNum) { 
		this.toolNum = toolNum;
	} 
	public int getIsJoin() { 
		return this.isJoin;
	} 
	@Column(value="group_is_join")
	public void setIsJoin(int isJoin) { 
		this.isJoin = isJoin;
	} 
	public int getJoinVerify() { 
		return this.joinVerify;
	} 
	@Column(value="group_join_verify")
	public void setJoinVerify(int joinVerify) { 
		this.joinVerify = joinVerify;
	} 
	public String getPromise() { 
		return this.promise;
	} 
	@Column(value="group_promise")
	public void setPromise(String promise) { 
		this.promise = promise;
	} 
	public long getPromiseCloudId() { 
		return this.promiseCloudId;
	} 
	@Column(value="group_promise_cloud_id")
	public void setPromiseCloudId(long promiseCloudId) { 
		this.promiseCloudId = promiseCloudId;
	} 
	public int getStatus() { 
		return this.status;
	} 
	@Column(value="group_status")
	public void setStatus(int status) { 
		this.status = status;
	} 
	public Date getUpdateTime() { 
		return this.updateTime;
	} 
	@Column(value="group_update_time")
	public void setUpdateTime(Date updateTime) { 
		this.updateTime = updateTime;
	} 
	public static int insert(IDao dao, long id, String name, String address, String zipcode, String linkman, String phone, String email, String job, int authNum, int giveNum, long userId, long buyTime, long expired, int expiredType, long cloudId, int toolNum, int isJoin, int joinVerify, String promise, long promiseCloudId, int status, Date updateTime) {
		return dao.insert(TABLE_NAME, new String[]{GROUP_ID, GROUP_NAME, GROUP_ADDRESS, GROUP_ZIPCODE, GROUP_LINKMAN, GROUP_PHONE, GROUP_EMAIL, GROUP_JOB, GROUP_AUTH_NUM, GROUP_GIVE_NUM, GROUP_USER_ID, GROUP_BUY_TIME, GROUP_EXPIRED, GROUP_EXPIRED_TYPE, GROUP_CLOUD_ID, GROUP_TOOL_NUM, GROUP_IS_JOIN, GROUP_JOIN_VERIFY, GROUP_PROMISE, GROUP_PROMISE_CLOUD_ID, GROUP_STATUS, GROUP_UPDATE_TIME}, id, name, address, zipcode, linkman, phone, email, job, authNum, giveNum, userId, buyTime, expired, expiredType, cloudId, toolNum, isJoin, joinVerify, promise, promiseCloudId, status, updateTime);
	}
	

	public static int updateById(IDao dao, long id, String name, String address, String zipcode, String linkman, String phone, String email, String job, int authNum, int giveNum, long userId, long buyTime, long expired, int expiredType, long cloudId, int toolNum, int isJoin, int joinVerify, String promise, long promiseCloudId, int status, Date updateTime) {
		return dao.update(TABLE_NAME, new String[]{GROUP_ID, GROUP_NAME, GROUP_ADDRESS, GROUP_ZIPCODE, GROUP_LINKMAN, GROUP_PHONE, GROUP_EMAIL, GROUP_JOB, GROUP_AUTH_NUM, GROUP_GIVE_NUM, GROUP_USER_ID, GROUP_BUY_TIME, GROUP_EXPIRED, GROUP_EXPIRED_TYPE, GROUP_CLOUD_ID, GROUP_TOOL_NUM, GROUP_IS_JOIN, GROUP_JOIN_VERIFY, GROUP_PROMISE, GROUP_PROMISE_CLOUD_ID, GROUP_STATUS, GROUP_UPDATE_TIME}, new String[]{GROUP_ID}, id, name, address, zipcode, linkman, phone, email, job, authNum, giveNum, userId, buyTime, expired, expiredType, cloudId, toolNum, isJoin, joinVerify, promise, promiseCloudId, status, updateTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{GROUP_ID}, id);
	}
	

	public static GroupEnterInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, GROUP_ID, GROUP_NAME, GROUP_ADDRESS, GROUP_ZIPCODE, GROUP_LINKMAN, GROUP_PHONE, GROUP_EMAIL, GROUP_JOB, GROUP_AUTH_NUM, GROUP_GIVE_NUM, GROUP_USER_ID, GROUP_BUY_TIME, GROUP_EXPIRED, GROUP_EXPIRED_TYPE, GROUP_CLOUD_ID, GROUP_TOOL_NUM, GROUP_IS_JOIN, GROUP_JOIN_VERIFY, GROUP_PROMISE, GROUP_PROMISE_CLOUD_ID, GROUP_STATUS, GROUP_UPDATE_TIME).whereTrue();
		sql.andEq(GROUP_ID).params(id);
		return dao.queryOne(GroupEnterInfo.class, sql);
	}
	

	public static List<GroupEnterInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, GROUP_ID, GROUP_NAME, GROUP_ADDRESS, GROUP_ZIPCODE, GROUP_LINKMAN, GROUP_PHONE, GROUP_EMAIL, GROUP_JOB, GROUP_AUTH_NUM, GROUP_GIVE_NUM, GROUP_USER_ID, GROUP_BUY_TIME, GROUP_EXPIRED, GROUP_EXPIRED_TYPE, GROUP_CLOUD_ID, GROUP_TOOL_NUM, GROUP_IS_JOIN, GROUP_JOIN_VERIFY, GROUP_PROMISE, GROUP_PROMISE_CLOUD_ID, GROUP_STATUS, GROUP_UPDATE_TIME).whereTrue();
		return dao.query(GroupEnterInfo.class, sql);
	}
	

	public static List<GroupEnterInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, GROUP_ID, GROUP_NAME, GROUP_ADDRESS, GROUP_ZIPCODE, GROUP_LINKMAN, GROUP_PHONE, GROUP_EMAIL, GROUP_JOB, GROUP_AUTH_NUM, GROUP_GIVE_NUM, GROUP_USER_ID, GROUP_BUY_TIME, GROUP_EXPIRED, GROUP_EXPIRED_TYPE, GROUP_CLOUD_ID, GROUP_TOOL_NUM, GROUP_IS_JOIN, GROUP_JOIN_VERIFY, GROUP_PROMISE, GROUP_PROMISE_CLOUD_ID, GROUP_STATUS, GROUP_UPDATE_TIME).whereTrue();
		return dao.query(paging, GroupEnterInfo.class, sql);
	}
	

} 