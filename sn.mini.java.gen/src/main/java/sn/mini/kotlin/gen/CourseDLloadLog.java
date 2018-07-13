package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("maker_course_dload_log")
public class CourseDLloadLog implements IDaoModel<CourseDLloadLog>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : maker_course_dload_log */
	public static final String TABLE_NAME = "maker_course_dload_log";
	/** :  course_dl_id */
	public static final String COURSE_DL_ID = "course_dl_id";
	/** :  course_dl_user_id */
	public static final String COURSE_DL_USER_ID = "course_dl_user_id";
	/** :  course_dl_cour_id */
	public static final String COURSE_DL_COUR_ID = "course_dl_cour_id";
	/** :  course_dl_create */
	public static final String COURSE_DL_CREATE = "course_dl_create";
	/**  */
	private long id;
	/**  */
	private long userId;
	/**  */
	private long courId;
	/**  */
	private Date create;
	public long getId() { 
		return this.id;
	} 
	@Column(value="course_dl_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="course_dl_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getCourId() { 
		return this.courId;
	} 
	@Column(value="course_dl_cour_id")
	public void setCourId(long courId) { 
		this.courId = courId;
	} 
	public Date getCreate() { 
		return this.create;
	} 
	@Column(value="course_dl_create")
	public void setCreate(Date create) { 
		this.create = create;
	} 
	public static int insert(IDao dao, long id, long userId, long courId, Date create) {
		return dao.insert(TABLE_NAME, new String[]{COURSE_DL_ID, COURSE_DL_USER_ID, COURSE_DL_COUR_ID, COURSE_DL_CREATE}, id, userId, courId, create);
	}
	

	public static int updateById(IDao dao, long id, long userId, long courId, Date create) {
		return dao.update(TABLE_NAME, new String[]{COURSE_DL_ID, COURSE_DL_USER_ID, COURSE_DL_COUR_ID, COURSE_DL_CREATE}, new String[]{COURSE_DL_ID}, id, userId, courId, create, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{COURSE_DL_ID}, id);
	}
	

	public static CourseDLloadLog findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, COURSE_DL_ID, COURSE_DL_USER_ID, COURSE_DL_COUR_ID, COURSE_DL_CREATE).whereTrue();
		sql.andEq(COURSE_DL_ID).params(id);
		return dao.queryOne(CourseDLloadLog.class, sql);
	}
	

	public static List<CourseDLloadLog> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, COURSE_DL_ID, COURSE_DL_USER_ID, COURSE_DL_COUR_ID, COURSE_DL_CREATE).whereTrue();
		return dao.query(CourseDLloadLog.class, sql);
	}
	

	public static List<CourseDLloadLog> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, COURSE_DL_ID, COURSE_DL_USER_ID, COURSE_DL_COUR_ID, COURSE_DL_CREATE).whereTrue();
		return dao.query(paging, CourseDLloadLog.class, sql);
	}
	

} 