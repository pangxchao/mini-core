package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("maker_template_info")
public class Template_info implements IDaoModel<Template_info>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : maker_template_info */
	public static final String TABLE_NAME = "maker_template_info";
	/** :  tmpl_id */
	public static final String TMPL_ID = "tmpl_id";
	/** :  tmpl_name */
	public static final String TMPL_NAME = "tmpl_name";
	/** :  tmpl_describe */
	public static final String TMPL_DESCRIBE = "tmpl_describe";
	/** :  tmpl_lable */
	public static final String TMPL_LABLE = "tmpl_lable";
	/** :  tmpl_file_id */
	public static final String TMPL_FILE_ID = "tmpl_file_id";
	/** :  tmpl_thumb */
	public static final String TMPL_THUMB = "tmpl_thumb";
	/** :  tmpl_times */
	public static final String TMPL_TIMES = "tmpl_times";
	/** :  tmpl_length */
	public static final String TMPL_LENGTH = "tmpl_length";
	/** :  tmpl_open */
	public static final String TMPL_OPEN = "tmpl_open";
	/** :  tmpl_state */
	public static final String TMPL_STATE = "tmpl_state";
	/** :  tmpl_user_id */
	public static final String TMPL_USER_ID = "tmpl_user_id";
	/** :  tmpl_create */
	public static final String TMPL_CREATE = "tmpl_create";
	/**  */
	private long id;
	/**  */
	private String name;
	/**  */
	private String describe;
	/**  */
	private String lable;
	/**  */
	private long fileId;
	/**  */
	private String thumb;
	/**  */
	private long times;
	/**  */
	private long length;
	/**  */
	private int open;
	/**  */
	private int state;
	/**  */
	private long userId;
	/**  */
	private Date create;
	public long getId() { 
		return this.id;
	} 
	@Column(value="tmpl_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getName() { 
		return this.name;
	} 
	@Column(value="tmpl_name")
	public void setName(String name) { 
		this.name = name;
	} 
	public String getDescribe() { 
		return this.describe;
	} 
	@Column(value="tmpl_describe")
	public void setDescribe(String describe) { 
		this.describe = describe;
	} 
	public String getLable() { 
		return this.lable;
	} 
	@Column(value="tmpl_lable")
	public void setLable(String lable) { 
		this.lable = lable;
	} 
	public long getFileId() { 
		return this.fileId;
	} 
	@Column(value="tmpl_file_id")
	public void setFileId(long fileId) { 
		this.fileId = fileId;
	} 
	public String getThumb() { 
		return this.thumb;
	} 
	@Column(value="tmpl_thumb")
	public void setThumb(String thumb) { 
		this.thumb = thumb;
	} 
	public long getTimes() { 
		return this.times;
	} 
	@Column(value="tmpl_times")
	public void setTimes(long times) { 
		this.times = times;
	} 
	public long getLength() { 
		return this.length;
	} 
	@Column(value="tmpl_length")
	public void setLength(long length) { 
		this.length = length;
	} 
	public int getOpen() { 
		return this.open;
	} 
	@Column(value="tmpl_open")
	public void setOpen(int open) { 
		this.open = open;
	} 
	public int getState() { 
		return this.state;
	} 
	@Column(value="tmpl_state")
	public void setState(int state) { 
		this.state = state;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="tmpl_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public Date getCreate() { 
		return this.create;
	} 
	@Column(value="tmpl_create")
	public void setCreate(Date create) { 
		this.create = create;
	} 
	public static int insert(IDao dao, long id, String name, String describe, String lable, long fileId, String thumb, long times, long length, int open, int state, long userId, Date create) {
		return dao.insert(TABLE_NAME, new String[]{TMPL_ID, TMPL_NAME, TMPL_DESCRIBE, TMPL_LABLE, TMPL_FILE_ID, TMPL_THUMB, TMPL_TIMES, TMPL_LENGTH, TMPL_OPEN, TMPL_STATE, TMPL_USER_ID, TMPL_CREATE}, id, name, describe, lable, fileId, thumb, times, length, open, state, userId, create);
	}
	

	public static int updateById(IDao dao, long id, String name, String describe, String lable, long fileId, String thumb, long times, long length, int open, int state, long userId, Date create) {
		return dao.update(TABLE_NAME, new String[]{TMPL_ID, TMPL_NAME, TMPL_DESCRIBE, TMPL_LABLE, TMPL_FILE_ID, TMPL_THUMB, TMPL_TIMES, TMPL_LENGTH, TMPL_OPEN, TMPL_STATE, TMPL_USER_ID, TMPL_CREATE}, new String[]{TMPL_ID}, id, name, describe, lable, fileId, thumb, times, length, open, state, userId, create, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{TMPL_ID}, id);
	}
	

	public static Template_info findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, TMPL_ID, TMPL_NAME, TMPL_DESCRIBE, TMPL_LABLE, TMPL_FILE_ID, TMPL_THUMB, TMPL_TIMES, TMPL_LENGTH, TMPL_OPEN, TMPL_STATE, TMPL_USER_ID, TMPL_CREATE).whereTrue();
		sql.andEq(TMPL_ID).params(id);
		return dao.queryOne(Template_info.class, sql);
	}
	

	public static List<Template_info> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, TMPL_ID, TMPL_NAME, TMPL_DESCRIBE, TMPL_LABLE, TMPL_FILE_ID, TMPL_THUMB, TMPL_TIMES, TMPL_LENGTH, TMPL_OPEN, TMPL_STATE, TMPL_USER_ID, TMPL_CREATE).whereTrue();
		return dao.query(Template_info.class, sql);
	}
	

	public static List<Template_info> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, TMPL_ID, TMPL_NAME, TMPL_DESCRIBE, TMPL_LABLE, TMPL_FILE_ID, TMPL_THUMB, TMPL_TIMES, TMPL_LENGTH, TMPL_OPEN, TMPL_STATE, TMPL_USER_ID, TMPL_CREATE).whereTrue();
		return dao.query(paging, Template_info.class, sql);
	}
	

} 