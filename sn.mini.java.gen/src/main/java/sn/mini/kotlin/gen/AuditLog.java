package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("maker_audit_log")
public class AuditLog implements IDaoModel<AuditLog>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : maker_audit_log */
	public static final String TABLE_NAME = "maker_audit_log";
	/** :  audit_id */
	public static final String AUDIT_ID = "audit_id";
	/** :  audit_user_id */
	public static final String AUDIT_USER_ID = "audit_user_id";
	/** :  audit_ref_id */
	public static final String AUDIT_REF_ID = "audit_ref_id";
	/** :  audit_ref_type */
	public static final String AUDIT_REF_TYPE = "audit_ref_type";
	/** :  audit_result */
	public static final String AUDIT_RESULT = "audit_result";
	/** :  audit_remaker */
	public static final String AUDIT_REMAKER = "audit_remaker";
	/** :  audit_create */
	public static final String AUDIT_CREATE = "audit_create";
	/**  */
	private long id;
	/**  */
	private long userId;
	/**  */
	private long refId;
	/**  */
	private int refType;
	/**  */
	private int result;
	/**  */
	private String remaker;
	/**  */
	private Date create;
	public long getId() { 
		return this.id;
	} 
	@Column(value="audit_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="audit_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getRefId() { 
		return this.refId;
	} 
	@Column(value="audit_ref_id")
	public void setRefId(long refId) { 
		this.refId = refId;
	} 
	public int getRefType() { 
		return this.refType;
	} 
	@Column(value="audit_ref_type")
	public void setRefType(int refType) { 
		this.refType = refType;
	} 
	public int getResult() { 
		return this.result;
	} 
	@Column(value="audit_result")
	public void setResult(int result) { 
		this.result = result;
	} 
	public String getRemaker() { 
		return this.remaker;
	} 
	@Column(value="audit_remaker")
	public void setRemaker(String remaker) { 
		this.remaker = remaker;
	} 
	public Date getCreate() { 
		return this.create;
	} 
	@Column(value="audit_create")
	public void setCreate(Date create) { 
		this.create = create;
	} 
	public static int insert(IDao dao, long id, long userId, long refId, int refType, int result, String remaker, Date create) {
		return dao.insert(TABLE_NAME, new String[]{AUDIT_ID, AUDIT_USER_ID, AUDIT_REF_ID, AUDIT_REF_TYPE, AUDIT_RESULT, AUDIT_REMAKER, AUDIT_CREATE}, id, userId, refId, refType, result, remaker, create);
	}
	

	public static int updateById(IDao dao, long id, long userId, long refId, int refType, int result, String remaker, Date create) {
		return dao.update(TABLE_NAME, new String[]{AUDIT_ID, AUDIT_USER_ID, AUDIT_REF_ID, AUDIT_REF_TYPE, AUDIT_RESULT, AUDIT_REMAKER, AUDIT_CREATE}, new String[]{AUDIT_ID}, id, userId, refId, refType, result, remaker, create, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{AUDIT_ID}, id);
	}
	

	public static AuditLog findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, AUDIT_ID, AUDIT_USER_ID, AUDIT_REF_ID, AUDIT_REF_TYPE, AUDIT_RESULT, AUDIT_REMAKER, AUDIT_CREATE).whereTrue();
		sql.andEq(AUDIT_ID).params(id);
		return dao.queryOne(AuditLog.class, sql);
	}
	

	public static List<AuditLog> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, AUDIT_ID, AUDIT_USER_ID, AUDIT_REF_ID, AUDIT_REF_TYPE, AUDIT_RESULT, AUDIT_REMAKER, AUDIT_CREATE).whereTrue();
		return dao.query(AuditLog.class, sql);
	}
	

	public static List<AuditLog> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, AUDIT_ID, AUDIT_USER_ID, AUDIT_REF_ID, AUDIT_REF_TYPE, AUDIT_RESULT, AUDIT_REMAKER, AUDIT_CREATE).whereTrue();
		return dao.query(paging, AuditLog.class, sql);
	}
	

} 