package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("new_session")
public class NewSession implements IDaoModel<NewSession>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : new_session */
	public static final String TABLE_NAME = "new_session";
	/** :  new_session_id */
	public static final String NEW_SESSION_ID = "new_session_id";
	/** :  new_session_user_id */
	public static final String NEW_SESSION_USER_ID = "new_session_user_id";
	/** :  new_session_coterie_id */
	public static final String NEW_SESSION_COTERIE_ID = "new_session_coterie_id";
	/** :  new_session_create_time */
	public static final String NEW_SESSION_CREATE_TIME = "new_session_create_time";
	/**  */
	private long id;
	/**  */
	private long userId;
	/**  */
	private long coterieId;
	/**  */
	private Date createTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="new_session_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="new_session_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getCoterieId() { 
		return this.coterieId;
	} 
	@Column(value="new_session_coterie_id")
	public void setCoterieId(long coterieId) { 
		this.coterieId = coterieId;
	} 
	public Date getCreateTime() { 
		return this.createTime;
	} 
	@Column(value="new_session_create_time")
	public void setCreateTime(Date createTime) { 
		this.createTime = createTime;
	} 
	public static int insert(IDao dao, long id, long userId, long coterieId, Date createTime) {
		return dao.insert(TABLE_NAME, new String[]{NEW_SESSION_ID, NEW_SESSION_USER_ID, NEW_SESSION_COTERIE_ID, NEW_SESSION_CREATE_TIME}, id, userId, coterieId, createTime);
	}
	

	public static int updateById(IDao dao, long id, long userId, long coterieId, Date createTime) {
		return dao.update(TABLE_NAME, new String[]{NEW_SESSION_ID, NEW_SESSION_USER_ID, NEW_SESSION_COTERIE_ID, NEW_SESSION_CREATE_TIME}, new String[]{NEW_SESSION_ID}, id, userId, coterieId, createTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{NEW_SESSION_ID}, id);
	}
	

	public static NewSession findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, NEW_SESSION_ID, NEW_SESSION_USER_ID, NEW_SESSION_COTERIE_ID, NEW_SESSION_CREATE_TIME).whereTrue();
		sql.andEq(NEW_SESSION_ID).params(id);
		return dao.queryOne(NewSession.class, sql);
	}
	

	public static List<NewSession> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, NEW_SESSION_ID, NEW_SESSION_USER_ID, NEW_SESSION_COTERIE_ID, NEW_SESSION_CREATE_TIME).whereTrue();
		return dao.query(NewSession.class, sql);
	}
	

	public static List<NewSession> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, NEW_SESSION_ID, NEW_SESSION_USER_ID, NEW_SESSION_COTERIE_ID, NEW_SESSION_CREATE_TIME).whereTrue();
		return dao.query(paging, NewSession.class, sql);
	}
	

} 