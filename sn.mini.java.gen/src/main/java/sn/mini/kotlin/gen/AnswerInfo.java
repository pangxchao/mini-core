package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("maker_answer_info")
public class AnswerInfo implements IDaoModel<AnswerInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : maker_answer_info */
	public static final String TABLE_NAME = "maker_answer_info";
	/** :  answer_id */
	public static final String ANSWER_ID = "answer_id";
	/** :  answer_file_id */
	public static final String ANSWER_FILE_ID = "answer_file_id";
	/** :  answer_times */
	public static final String ANSWER_TIMES = "answer_times";
	/** :  answer_length */
	public static final String ANSWER_LENGTH = "answer_length";
	/** :  answer_mark_file_id */
	public static final String ANSWER_MARK_FILE_ID = "answer_mark_file_id";
	/** :  answer_mark_times */
	public static final String ANSWER_MARK_TIMES = "answer_mark_times";
	/** :  answer_mark_length */
	public static final String ANSWER_MARK_LENGTH = "answer_mark_length";
	/** :  answer_user_id */
	public static final String ANSWER_USER_ID = "answer_user_id";
	/** :  answer_course_id */
	public static final String ANSWER_COURSE_ID = "answer_course_id";
	/** :  answer_utime */
	public static final String ANSWER_UTIME = "answer_utime";
	/** :  answer_ctime */
	public static final String ANSWER_CTIME = "answer_ctime";
	/**  */
	private long id;
	/**  */
	private long fileId;
	/**  */
	private long times;
	/**  */
	private long length;
	/**  */
	private long markFileId;
	/**  */
	private long markTimes;
	/**  */
	private long markLength;
	/**  */
	private long userId;
	/**  */
	private long courseId;
	/**  */
	private Date utime;
	/**  */
	private Date ctime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="answer_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getFileId() { 
		return this.fileId;
	} 
	@Column(value="answer_file_id")
	public void setFileId(long fileId) { 
		this.fileId = fileId;
	} 
	public long getTimes() { 
		return this.times;
	} 
	@Column(value="answer_times")
	public void setTimes(long times) { 
		this.times = times;
	} 
	public long getLength() { 
		return this.length;
	} 
	@Column(value="answer_length")
	public void setLength(long length) { 
		this.length = length;
	} 
	public long getMarkFileId() { 
		return this.markFileId;
	} 
	@Column(value="answer_mark_file_id")
	public void setMarkFileId(long markFileId) { 
		this.markFileId = markFileId;
	} 
	public long getMarkTimes() { 
		return this.markTimes;
	} 
	@Column(value="answer_mark_times")
	public void setMarkTimes(long markTimes) { 
		this.markTimes = markTimes;
	} 
	public long getMarkLength() { 
		return this.markLength;
	} 
	@Column(value="answer_mark_length")
	public void setMarkLength(long markLength) { 
		this.markLength = markLength;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="answer_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getCourseId() { 
		return this.courseId;
	} 
	@Column(value="answer_course_id")
	public void setCourseId(long courseId) { 
		this.courseId = courseId;
	} 
	public Date getUtime() { 
		return this.utime;
	} 
	@Column(value="answer_utime")
	public void setUtime(Date utime) { 
		this.utime = utime;
	} 
	public Date getCtime() { 
		return this.ctime;
	} 
	@Column(value="answer_ctime")
	public void setCtime(Date ctime) { 
		this.ctime = ctime;
	} 
	public static int insert(IDao dao, long id, long fileId, long times, long length, long markFileId, long markTimes, long markLength, long userId, long courseId, Date utime, Date ctime) {
		return dao.insert(TABLE_NAME, new String[]{ANSWER_ID, ANSWER_FILE_ID, ANSWER_TIMES, ANSWER_LENGTH, ANSWER_MARK_FILE_ID, ANSWER_MARK_TIMES, ANSWER_MARK_LENGTH, ANSWER_USER_ID, ANSWER_COURSE_ID, ANSWER_UTIME, ANSWER_CTIME}, id, fileId, times, length, markFileId, markTimes, markLength, userId, courseId, utime, ctime);
	}
	

	public static int updateById(IDao dao, long id, long fileId, long times, long length, long markFileId, long markTimes, long markLength, long userId, long courseId, Date utime, Date ctime) {
		return dao.update(TABLE_NAME, new String[]{ANSWER_ID, ANSWER_FILE_ID, ANSWER_TIMES, ANSWER_LENGTH, ANSWER_MARK_FILE_ID, ANSWER_MARK_TIMES, ANSWER_MARK_LENGTH, ANSWER_USER_ID, ANSWER_COURSE_ID, ANSWER_UTIME, ANSWER_CTIME}, new String[]{ANSWER_ID}, id, fileId, times, length, markFileId, markTimes, markLength, userId, courseId, utime, ctime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{ANSWER_ID}, id);
	}
	

	public static AnswerInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, ANSWER_ID, ANSWER_FILE_ID, ANSWER_TIMES, ANSWER_LENGTH, ANSWER_MARK_FILE_ID, ANSWER_MARK_TIMES, ANSWER_MARK_LENGTH, ANSWER_USER_ID, ANSWER_COURSE_ID, ANSWER_UTIME, ANSWER_CTIME).whereTrue();
		sql.andEq(ANSWER_ID).params(id);
		return dao.queryOne(AnswerInfo.class, sql);
	}
	

	public static List<AnswerInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, ANSWER_ID, ANSWER_FILE_ID, ANSWER_TIMES, ANSWER_LENGTH, ANSWER_MARK_FILE_ID, ANSWER_MARK_TIMES, ANSWER_MARK_LENGTH, ANSWER_USER_ID, ANSWER_COURSE_ID, ANSWER_UTIME, ANSWER_CTIME).whereTrue();
		return dao.query(AnswerInfo.class, sql);
	}
	

	public static List<AnswerInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, ANSWER_ID, ANSWER_FILE_ID, ANSWER_TIMES, ANSWER_LENGTH, ANSWER_MARK_FILE_ID, ANSWER_MARK_TIMES, ANSWER_MARK_LENGTH, ANSWER_USER_ID, ANSWER_COURSE_ID, ANSWER_UTIME, ANSWER_CTIME).whereTrue();
		return dao.query(paging, AnswerInfo.class, sql);
	}
	

} 