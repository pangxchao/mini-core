package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
@Table("user_session")
public class UserSession implements IDaoModel<UserSession>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : user_session */
	public static final String TABLE_NAME = "user_session";
	/** session 用户ID:  session_id */
	public static final String SESSION_ID = "session_id";
	/** :  session_user_id */
	public static final String SESSION_USER_ID = "session_user_id";
	/** :  session_token */
	public static final String SESSION_TOKEN = "session_token";
	/** :  session_valid */
	public static final String SESSION_VALID = "session_valid";
	/** 最后更新session 时间:  session_time */
	public static final String SESSION_TIME = "session_time";
	/** session 用户ID */
	private long id;
	/**  */
	private long userId;
	/**  */
	private String token;
	/**  */
	private long valid;
	/** 最后更新session 时间 */
	private long time;
	public long getId() { 
		return this.id;
	} 
	@Column(value="session_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="session_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public String getToken() { 
		return this.token;
	} 
	@Column(value="session_token")
	public void setToken(String token) { 
		this.token = token;
	} 
	public long getValid() { 
		return this.valid;
	} 
	@Column(value="session_valid")
	public void setValid(long valid) { 
		this.valid = valid;
	} 
	public long getTime() { 
		return this.time;
	} 
	@Column(value="session_time")
	public void setTime(long time) { 
		this.time = time;
	} 
	public static int insert(IDao dao, long id, long userId, String token, long valid, long time) {
		return dao.insert(TABLE_NAME, new String[]{SESSION_ID, SESSION_USER_ID, SESSION_TOKEN, SESSION_VALID, SESSION_TIME}, id, userId, token, valid, time);
	}
	

	public static int updateById(IDao dao, long id, long userId, String token, long valid, long time) {
		return dao.update(TABLE_NAME, new String[]{SESSION_ID, SESSION_USER_ID, SESSION_TOKEN, SESSION_VALID, SESSION_TIME}, new String[]{SESSION_ID}, id, userId, token, valid, time, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{SESSION_ID}, id);
	}
	

	public static UserSession findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, SESSION_ID, SESSION_USER_ID, SESSION_TOKEN, SESSION_VALID, SESSION_TIME).whereTrue();
		sql.andEq(SESSION_ID).params(id);
		return dao.queryOne(UserSession.class, sql);
	}
	

	public static List<UserSession> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, SESSION_ID, SESSION_USER_ID, SESSION_TOKEN, SESSION_VALID, SESSION_TIME).whereTrue();
		return dao.query(UserSession.class, sql);
	}
	

	public static List<UserSession> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, SESSION_ID, SESSION_USER_ID, SESSION_TOKEN, SESSION_VALID, SESSION_TIME).whereTrue();
		return dao.query(paging, UserSession.class, sql);
	}
	

} 