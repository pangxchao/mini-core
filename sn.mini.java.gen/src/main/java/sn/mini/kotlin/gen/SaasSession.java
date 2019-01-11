package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("saas_session")
public class SaasSession implements IDaoModel<SaasSession>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : saas_session */
	public static final String TABLE_NAME = "saas_session";
	/** :  saas_session_id */
	public static final String SAAS_SESSION_ID = "saas_session_id";
	/** :  saas_session_token */
	public static final String SAAS_SESSION_TOKEN = "saas_session_token";
	/** :  saas_session_user_id */
	public static final String SAAS_SESSION_USER_ID = "saas_session_user_id";
	/** :  saas_session_create_time */
	public static final String SAAS_SESSION_CREATE_TIME = "saas_session_create_time";
	/**  */
	private long id;
	/**  */
	private String token;
	/**  */
	private long userId;
	/**  */
	private Date createTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="saas_session_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getToken() { 
		return this.token;
	} 
	@Column(value="saas_session_token")
	public void setToken(String token) { 
		this.token = token;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="saas_session_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public Date getCreateTime() { 
		return this.createTime;
	} 
	@Column(value="saas_session_create_time")
	public void setCreateTime(Date createTime) { 
		this.createTime = createTime;
	} 
	public static int insert(IDao dao, long id, String token, long userId, Date createTime) {
		return dao.insert(TABLE_NAME, new String[]{SAAS_SESSION_ID, SAAS_SESSION_TOKEN, SAAS_SESSION_USER_ID, SAAS_SESSION_CREATE_TIME}, id, token, userId, createTime);
	}
	

	public static int updateById(IDao dao, long id, String token, long userId, Date createTime) {
		return dao.update(TABLE_NAME, new String[]{SAAS_SESSION_ID, SAAS_SESSION_TOKEN, SAAS_SESSION_USER_ID, SAAS_SESSION_CREATE_TIME}, new String[]{SAAS_SESSION_ID}, id, token, userId, createTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{SAAS_SESSION_ID}, id);
	}
	

	public static SaasSession findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_SESSION_ID, SAAS_SESSION_TOKEN, SAAS_SESSION_USER_ID, SAAS_SESSION_CREATE_TIME).whereTrue();
		sql.andEq(SAAS_SESSION_ID).params(id);
		return dao.queryOne(SaasSession.class, sql);
	}
	

	public static List<SaasSession> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_SESSION_ID, SAAS_SESSION_TOKEN, SAAS_SESSION_USER_ID, SAAS_SESSION_CREATE_TIME).whereTrue();
		return dao.query(SaasSession.class, sql);
	}
	

	public static List<SaasSession> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_SESSION_ID, SAAS_SESSION_TOKEN, SAAS_SESSION_USER_ID, SAAS_SESSION_CREATE_TIME).whereTrue();
		return dao.query(paging, SaasSession.class, sql);
	}
	

} 