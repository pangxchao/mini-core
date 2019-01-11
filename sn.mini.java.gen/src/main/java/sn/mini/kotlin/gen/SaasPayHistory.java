package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("saas_pay_history")
public class SaasPayHistory implements IDaoModel<SaasPayHistory>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : saas_pay_history */
	public static final String TABLE_NAME = "saas_pay_history";
	/** :  saas_pay_id */
	public static final String SAAS_PAY_ID = "saas_pay_id";
	/** :  saas_pay_m_id */
	public static final String SAAS_PAY_M_ID = "saas_pay_m_id";
	/** :  saas_pay_tenant_id */
	public static final String SAAS_PAY_TENANT_ID = "saas_pay_tenant_id";
	/** :  saas_pay_app_id */
	public static final String SAAS_PAY_APP_ID = "saas_pay_app_id";
	/** :  saas_pay_app_type */
	public static final String SAAS_PAY_APP_TYPE = "saas_pay_app_type";
	/** :  saas_pay_user_id */
	public static final String SAAS_PAY_USER_ID = "saas_pay_user_id";
	/** :  saas_pay_create_time */
	public static final String SAAS_PAY_CREATE_TIME = "saas_pay_create_time";
	/**  */
	private long id;
	/**  */
	private String mId;
	/**  */
	private String tenantId;
	/**  */
	private String appId;
	/**  */
	private String appType;
	/**  */
	private long userId;
	/**  */
	private Date createTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="saas_pay_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getMId() { 
		return this.mId;
	} 
	@Column(value="saas_pay_m_id")
	public void setMId(String mId) { 
		this.mId = mId;
	} 
	public String getTenantId() { 
		return this.tenantId;
	} 
	@Column(value="saas_pay_tenant_id")
	public void setTenantId(String tenantId) { 
		this.tenantId = tenantId;
	} 
	public String getAppId() { 
		return this.appId;
	} 
	@Column(value="saas_pay_app_id")
	public void setAppId(String appId) { 
		this.appId = appId;
	} 
	public String getAppType() { 
		return this.appType;
	} 
	@Column(value="saas_pay_app_type")
	public void setAppType(String appType) { 
		this.appType = appType;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="saas_pay_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public Date getCreateTime() { 
		return this.createTime;
	} 
	@Column(value="saas_pay_create_time")
	public void setCreateTime(Date createTime) { 
		this.createTime = createTime;
	} 
	public static int insert(IDao dao, long id, String mId, String tenantId, String appId, String appType, long userId, Date createTime) {
		return dao.insert(TABLE_NAME, new String[]{SAAS_PAY_ID, SAAS_PAY_M_ID, SAAS_PAY_TENANT_ID, SAAS_PAY_APP_ID, SAAS_PAY_APP_TYPE, SAAS_PAY_USER_ID, SAAS_PAY_CREATE_TIME}, id, mId, tenantId, appId, appType, userId, createTime);
	}
	

	public static int updateById(IDao dao, long id, String mId, String tenantId, String appId, String appType, long userId, Date createTime) {
		return dao.update(TABLE_NAME, new String[]{SAAS_PAY_ID, SAAS_PAY_M_ID, SAAS_PAY_TENANT_ID, SAAS_PAY_APP_ID, SAAS_PAY_APP_TYPE, SAAS_PAY_USER_ID, SAAS_PAY_CREATE_TIME}, new String[]{SAAS_PAY_ID}, id, mId, tenantId, appId, appType, userId, createTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{SAAS_PAY_ID}, id);
	}
	

	public static SaasPayHistory findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_PAY_ID, SAAS_PAY_M_ID, SAAS_PAY_TENANT_ID, SAAS_PAY_APP_ID, SAAS_PAY_APP_TYPE, SAAS_PAY_USER_ID, SAAS_PAY_CREATE_TIME).whereTrue();
		sql.andEq(SAAS_PAY_ID).params(id);
		return dao.queryOne(SaasPayHistory.class, sql);
	}
	

	public static List<SaasPayHistory> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_PAY_ID, SAAS_PAY_M_ID, SAAS_PAY_TENANT_ID, SAAS_PAY_APP_ID, SAAS_PAY_APP_TYPE, SAAS_PAY_USER_ID, SAAS_PAY_CREATE_TIME).whereTrue();
		return dao.query(SaasPayHistory.class, sql);
	}
	

	public static List<SaasPayHistory> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_PAY_ID, SAAS_PAY_M_ID, SAAS_PAY_TENANT_ID, SAAS_PAY_APP_ID, SAAS_PAY_APP_TYPE, SAAS_PAY_USER_ID, SAAS_PAY_CREATE_TIME).whereTrue();
		return dao.query(paging, SaasPayHistory.class, sql);
	}
	

} 