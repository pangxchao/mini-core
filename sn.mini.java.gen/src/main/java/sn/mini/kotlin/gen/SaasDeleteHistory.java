package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("saas_delete_history")
public class SaasDeleteHistory implements IDaoModel<SaasDeleteHistory>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : saas_delete_history */
	public static final String TABLE_NAME = "saas_delete_history";
	/** :  saas_delete_id */
	public static final String SAAS_DELETE_ID = "saas_delete_id";
	/** :  saas_delete_m_id */
	public static final String SAAS_DELETE_M_ID = "saas_delete_m_id";
	/** :  saas_delete_tenant_id */
	public static final String SAAS_DELETE_TENANT_ID = "saas_delete_tenant_id";
	/** :  saas_delete_app_id */
	public static final String SAAS_DELETE_APP_ID = "saas_delete_app_id";
	/** :  saas_delete_user_id */
	public static final String SAAS_DELETE_USER_ID = "saas_delete_user_id";
	/** :  saas_delete_create_time */
	public static final String SAAS_DELETE_CREATE_TIME = "saas_delete_create_time";
	/**  */
	private long id;
	/**  */
	private String mId;
	/**  */
	private String tenantId;
	/**  */
	private String appId;
	/**  */
	private long userId;
	/**  */
	private Date createTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="saas_delete_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getMId() { 
		return this.mId;
	} 
	@Column(value="saas_delete_m_id")
	public void setMId(String mId) { 
		this.mId = mId;
	} 
	public String getTenantId() { 
		return this.tenantId;
	} 
	@Column(value="saas_delete_tenant_id")
	public void setTenantId(String tenantId) { 
		this.tenantId = tenantId;
	} 
	public String getAppId() { 
		return this.appId;
	} 
	@Column(value="saas_delete_app_id")
	public void setAppId(String appId) { 
		this.appId = appId;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="saas_delete_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public Date getCreateTime() { 
		return this.createTime;
	} 
	@Column(value="saas_delete_create_time")
	public void setCreateTime(Date createTime) { 
		this.createTime = createTime;
	} 
	public static int insert(IDao dao, long id, String mId, String tenantId, String appId, long userId, Date createTime) {
		return dao.insert(TABLE_NAME, new String[]{SAAS_DELETE_ID, SAAS_DELETE_M_ID, SAAS_DELETE_TENANT_ID, SAAS_DELETE_APP_ID, SAAS_DELETE_USER_ID, SAAS_DELETE_CREATE_TIME}, id, mId, tenantId, appId, userId, createTime);
	}
	

	public static int updateById(IDao dao, long id, String mId, String tenantId, String appId, long userId, Date createTime) {
		return dao.update(TABLE_NAME, new String[]{SAAS_DELETE_ID, SAAS_DELETE_M_ID, SAAS_DELETE_TENANT_ID, SAAS_DELETE_APP_ID, SAAS_DELETE_USER_ID, SAAS_DELETE_CREATE_TIME}, new String[]{SAAS_DELETE_ID}, id, mId, tenantId, appId, userId, createTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{SAAS_DELETE_ID}, id);
	}
	

	public static SaasDeleteHistory findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_DELETE_ID, SAAS_DELETE_M_ID, SAAS_DELETE_TENANT_ID, SAAS_DELETE_APP_ID, SAAS_DELETE_USER_ID, SAAS_DELETE_CREATE_TIME).whereTrue();
		sql.andEq(SAAS_DELETE_ID).params(id);
		return dao.queryOne(SaasDeleteHistory.class, sql);
	}
	

	public static List<SaasDeleteHistory> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_DELETE_ID, SAAS_DELETE_M_ID, SAAS_DELETE_TENANT_ID, SAAS_DELETE_APP_ID, SAAS_DELETE_USER_ID, SAAS_DELETE_CREATE_TIME).whereTrue();
		return dao.query(SaasDeleteHistory.class, sql);
	}
	

	public static List<SaasDeleteHistory> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_DELETE_ID, SAAS_DELETE_M_ID, SAAS_DELETE_TENANT_ID, SAAS_DELETE_APP_ID, SAAS_DELETE_USER_ID, SAAS_DELETE_CREATE_TIME).whereTrue();
		return dao.query(paging, SaasDeleteHistory.class, sql);
	}
	

} 