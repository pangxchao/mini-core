package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("maker_play_logger")
public class PlayLogger implements IDaoModel<PlayLogger>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : maker_play_logger */
	public static final String TABLE_NAME = "maker_play_logger";
	/** :  play_id */
	public static final String PLAY_ID = "play_id";
	/** :  play_user_id */
	public static final String PLAY_USER_ID = "play_user_id";
	/** :  play_times */
	public static final String PLAY_TIMES = "play_times";
	/** :  play_course_id */
	public static final String PLAY_COURSE_ID = "play_course_id";
	/** :  play_utime */
	public static final String PLAY_UTIME = "play_utime";
	/** :  play_ctime */
	public static final String PLAY_CTIME = "play_ctime";
	/**  */
	private long id;
	/**  */
	private long userId;
	/**  */
	private long times;
	/**  */
	private long courseId;
	/**  */
	private Date utime;
	/**  */
	private Date ctime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="play_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="play_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getTimes() { 
		return this.times;
	} 
	@Column(value="play_times")
	public void setTimes(long times) { 
		this.times = times;
	} 
	public long getCourseId() { 
		return this.courseId;
	} 
	@Column(value="play_course_id")
	public void setCourseId(long courseId) { 
		this.courseId = courseId;
	} 
	public Date getUtime() { 
		return this.utime;
	} 
	@Column(value="play_utime")
	public void setUtime(Date utime) { 
		this.utime = utime;
	} 
	public Date getCtime() { 
		return this.ctime;
	} 
	@Column(value="play_ctime")
	public void setCtime(Date ctime) { 
		this.ctime = ctime;
	} 
	public static int insert(IDao dao, long id, long userId, long times, long courseId, Date utime, Date ctime) {
		return dao.insert(TABLE_NAME, new String[]{PLAY_ID, PLAY_USER_ID, PLAY_TIMES, PLAY_COURSE_ID, PLAY_UTIME, PLAY_CTIME}, id, userId, times, courseId, utime, ctime);
	}
	

	public static int updateById(IDao dao, long id, long userId, long times, long courseId, Date utime, Date ctime) {
		return dao.update(TABLE_NAME, new String[]{PLAY_ID, PLAY_USER_ID, PLAY_TIMES, PLAY_COURSE_ID, PLAY_UTIME, PLAY_CTIME}, new String[]{PLAY_ID}, id, userId, times, courseId, utime, ctime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{PLAY_ID}, id);
	}
	

	public static PlayLogger findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, PLAY_ID, PLAY_USER_ID, PLAY_TIMES, PLAY_COURSE_ID, PLAY_UTIME, PLAY_CTIME).whereTrue();
		sql.andEq(PLAY_ID).params(id);
		return dao.queryOne(PlayLogger.class, sql);
	}
	

	public static List<PlayLogger> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, PLAY_ID, PLAY_USER_ID, PLAY_TIMES, PLAY_COURSE_ID, PLAY_UTIME, PLAY_CTIME).whereTrue();
		return dao.query(PlayLogger.class, sql);
	}
	

	public static List<PlayLogger> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, PLAY_ID, PLAY_USER_ID, PLAY_TIMES, PLAY_COURSE_ID, PLAY_UTIME, PLAY_CTIME).whereTrue();
		return dao.query(paging, PlayLogger.class, sql);
	}
	

} 