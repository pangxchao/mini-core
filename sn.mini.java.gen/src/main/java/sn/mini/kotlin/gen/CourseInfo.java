package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("maker_course_info")
public class CourseInfo implements IDaoModel<CourseInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : maker_course_info */
	public static final String TABLE_NAME = "maker_course_info";
	/** :  course_id */
	public static final String COURSE_ID = "course_id";
	/** :  course_name */
	public static final String COURSE_NAME = "course_name";
	/** :  course_describe */
	public static final String COURSE_DESCRIBE = "course_describe";
	/** :  course_lable */
	public static final String COURSE_LABLE = "course_lable";
	/** :  course_thumb */
	public static final String COURSE_THUMB = "course_thumb";
	/** :  course_file_id */
	public static final String COURSE_FILE_ID = "course_file_id";
	/** :  course_author */
	public static final String COURSE_AUTHOR = "course_author";
	/** :  course_times */
	public static final String COURSE_TIMES = "course_times";
	/** :  course_length */
	public static final String COURSE_LENGTH = "course_length";
	/** :  course_file */
	public static final String COURSE_FILE = "course_file";
	/** :  course_open */
	public static final String COURSE_OPEN = "course_open";
	/** :  course_edit */
	public static final String COURSE_EDIT = "course_edit";
	/** :  course_state */
	public static final String COURSE_STATE = "course_state";
	/** :  course_guid */
	public static final String COURSE_GUID = "course_guid";
	/** :  course_user_id */
	public static final String COURSE_USER_ID = "course_user_id";
	/** :  course_utime */
	public static final String COURSE_UTIME = "course_utime";
	/** :  course_ctime */
	public static final String COURSE_CTIME = "course_ctime";
	/**  */
	private long id;
	/**  */
	private String name;
	/**  */
	private String describe;
	/**  */
	private String lable;
	/**  */
	private String thumb;
	/**  */
	private long fileId;
	/**  */
	private String author;
	/**  */
	private long times;
	/**  */
	private long length;
	/**  */
	private int file;
	/**  */
	private int open;
	/**  */
	private int edit;
	/**  */
	private int state;
	/**  */
	private String guid;
	/**  */
	private long userId;
	/**  */
	private Date utime;
	/**  */
	private Date ctime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="course_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getName() { 
		return this.name;
	} 
	@Column(value="course_name")
	public void setName(String name) { 
		this.name = name;
	} 
	public String getDescribe() { 
		return this.describe;
	} 
	@Column(value="course_describe")
	public void setDescribe(String describe) { 
		this.describe = describe;
	} 
	public String getLable() { 
		return this.lable;
	} 
	@Column(value="course_lable")
	public void setLable(String lable) { 
		this.lable = lable;
	} 
	public String getThumb() { 
		return this.thumb;
	} 
	@Column(value="course_thumb")
	public void setThumb(String thumb) { 
		this.thumb = thumb;
	} 
	public long getFileId() { 
		return this.fileId;
	} 
	@Column(value="course_file_id")
	public void setFileId(long fileId) { 
		this.fileId = fileId;
	} 
	public String getAuthor() { 
		return this.author;
	} 
	@Column(value="course_author")
	public void setAuthor(String author) { 
		this.author = author;
	} 
	public long getTimes() { 
		return this.times;
	} 
	@Column(value="course_times")
	public void setTimes(long times) { 
		this.times = times;
	} 
	public long getLength() { 
		return this.length;
	} 
	@Column(value="course_length")
	public void setLength(long length) { 
		this.length = length;
	} 
	public int getFile() { 
		return this.file;
	} 
	@Column(value="course_file")
	public void setFile(int file) { 
		this.file = file;
	} 
	public int getOpen() { 
		return this.open;
	} 
	@Column(value="course_open")
	public void setOpen(int open) { 
		this.open = open;
	} 
	public int getEdit() { 
		return this.edit;
	} 
	@Column(value="course_edit")
	public void setEdit(int edit) { 
		this.edit = edit;
	} 
	public int getState() { 
		return this.state;
	} 
	@Column(value="course_state")
	public void setState(int state) { 
		this.state = state;
	} 
	public String getGuid() { 
		return this.guid;
	} 
	@Column(value="course_guid")
	public void setGuid(String guid) { 
		this.guid = guid;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="course_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public Date getUtime() { 
		return this.utime;
	} 
	@Column(value="course_utime")
	public void setUtime(Date utime) { 
		this.utime = utime;
	} 
	public Date getCtime() { 
		return this.ctime;
	} 
	@Column(value="course_ctime")
	public void setCtime(Date ctime) { 
		this.ctime = ctime;
	} 
	public static int insert(IDao dao, long id, String name, String describe, String lable, String thumb, long fileId, String author, long times, long length, int file, int open, int edit, int state, String guid, long userId, Date utime, Date ctime) {
		return dao.insert(TABLE_NAME, new String[]{COURSE_ID, COURSE_NAME, COURSE_DESCRIBE, COURSE_LABLE, COURSE_THUMB, COURSE_FILE_ID, COURSE_AUTHOR, COURSE_TIMES, COURSE_LENGTH, COURSE_FILE, COURSE_OPEN, COURSE_EDIT, COURSE_STATE, COURSE_GUID, COURSE_USER_ID, COURSE_UTIME, COURSE_CTIME}, id, name, describe, lable, thumb, fileId, author, times, length, file, open, edit, state, guid, userId, utime, ctime);
	}
	

	public static int updateById(IDao dao, long id, String name, String describe, String lable, String thumb, long fileId, String author, long times, long length, int file, int open, int edit, int state, String guid, long userId, Date utime, Date ctime) {
		return dao.update(TABLE_NAME, new String[]{COURSE_ID, COURSE_NAME, COURSE_DESCRIBE, COURSE_LABLE, COURSE_THUMB, COURSE_FILE_ID, COURSE_AUTHOR, COURSE_TIMES, COURSE_LENGTH, COURSE_FILE, COURSE_OPEN, COURSE_EDIT, COURSE_STATE, COURSE_GUID, COURSE_USER_ID, COURSE_UTIME, COURSE_CTIME}, new String[]{COURSE_ID}, id, name, describe, lable, thumb, fileId, author, times, length, file, open, edit, state, guid, userId, utime, ctime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{COURSE_ID}, id);
	}
	

	public static CourseInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, COURSE_ID, COURSE_NAME, COURSE_DESCRIBE, COURSE_LABLE, COURSE_THUMB, COURSE_FILE_ID, COURSE_AUTHOR, COURSE_TIMES, COURSE_LENGTH, COURSE_FILE, COURSE_OPEN, COURSE_EDIT, COURSE_STATE, COURSE_GUID, COURSE_USER_ID, COURSE_UTIME, COURSE_CTIME).whereTrue();
		sql.andEq(COURSE_ID).params(id);
		return dao.queryOne(CourseInfo.class, sql);
	}
	

	public static List<CourseInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, COURSE_ID, COURSE_NAME, COURSE_DESCRIBE, COURSE_LABLE, COURSE_THUMB, COURSE_FILE_ID, COURSE_AUTHOR, COURSE_TIMES, COURSE_LENGTH, COURSE_FILE, COURSE_OPEN, COURSE_EDIT, COURSE_STATE, COURSE_GUID, COURSE_USER_ID, COURSE_UTIME, COURSE_CTIME).whereTrue();
		return dao.query(CourseInfo.class, sql);
	}
	

	public static List<CourseInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, COURSE_ID, COURSE_NAME, COURSE_DESCRIBE, COURSE_LABLE, COURSE_THUMB, COURSE_FILE_ID, COURSE_AUTHOR, COURSE_TIMES, COURSE_LENGTH, COURSE_FILE, COURSE_OPEN, COURSE_EDIT, COURSE_STATE, COURSE_GUID, COURSE_USER_ID, COURSE_UTIME, COURSE_CTIME).whereTrue();
		return dao.query(paging, CourseInfo.class, sql);
	}
	

} 