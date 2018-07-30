package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
@Table("external_user")
public class ExternalUser implements IDaoModel<ExternalUser>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : external_user */
	public static final String TABLE_NAME = "external_user";
	/** :  external_id */
	public static final String EXTERNAL_ID = "external_id";
	/** :  external_user_id */
	public static final String EXTERNAL_USER_ID = "external_user_id";
	/** :  external_token */
	public static final String EXTERNAL_TOKEN = "external_token";
	/** :  external_name */
	public static final String EXTERNAL_NAME = "external_name";
	/** :  external_time */
	public static final String EXTERNAL_TIME = "external_time";
	/**  */
	private long id;
	/**  */
	private String userId;
	/**  */
	private String token;
	/**  */
	private String name;
	/**  */
	private long time;
	public long getId() { 
		return this.id;
	} 
	@Column(value="external_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getUserId() { 
		return this.userId;
	} 
	@Column(value="external_user_id")
	public void setUserId(String userId) { 
		this.userId = userId;
	} 
	public String getToken() { 
		return this.token;
	} 
	@Column(value="external_token")
	public void setToken(String token) { 
		this.token = token;
	} 
	public String getName() { 
		return this.name;
	} 
	@Column(value="external_name")
	public void setName(String name) { 
		this.name = name;
	} 
	public long getTime() { 
		return this.time;
	} 
	@Column(value="external_time")
	public void setTime(long time) { 
		this.time = time;
	} 
	public static int insert(IDao dao, long id, String userId, String token, String name, long time) {
		return dao.insert(TABLE_NAME, new String[]{EXTERNAL_ID, EXTERNAL_USER_ID, EXTERNAL_TOKEN, EXTERNAL_NAME, EXTERNAL_TIME}, id, userId, token, name, time);
	}
	

	public static int updateById(IDao dao, long id, String userId, String token, String name, long time) {
		return dao.update(TABLE_NAME, new String[]{EXTERNAL_ID, EXTERNAL_USER_ID, EXTERNAL_TOKEN, EXTERNAL_NAME, EXTERNAL_TIME}, new String[]{EXTERNAL_ID}, id, userId, token, name, time, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{EXTERNAL_ID}, id);
	}
	

	public static ExternalUser findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, EXTERNAL_ID, EXTERNAL_USER_ID, EXTERNAL_TOKEN, EXTERNAL_NAME, EXTERNAL_TIME).whereTrue();
		sql.andEq(EXTERNAL_ID).params(id);
		return dao.queryOne(ExternalUser.class, sql);
	}
	

	public static List<ExternalUser> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, EXTERNAL_ID, EXTERNAL_USER_ID, EXTERNAL_TOKEN, EXTERNAL_NAME, EXTERNAL_TIME).whereTrue();
		return dao.query(ExternalUser.class, sql);
	}
	

	public static List<ExternalUser> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, EXTERNAL_ID, EXTERNAL_USER_ID, EXTERNAL_TOKEN, EXTERNAL_NAME, EXTERNAL_TIME).whereTrue();
		return dao.query(paging, ExternalUser.class, sql);
	}
	

} 