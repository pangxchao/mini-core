package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("maker_resource_info")
public class ResourceInfo implements IDaoModel<ResourceInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : maker_resource_info */
	public static final String TABLE_NAME = "maker_resource_info";
	/** :  res_id */
	public static final String RES_ID = "res_id";
	/** :  res_name */
	public static final String RES_NAME = "res_name";
	/** :  res_describe */
	public static final String RES_DESCRIBE = "res_describe";
	/** :  res_lable */
	public static final String RES_LABLE = "res_lable";
	/** :  res_file_id */
	public static final String RES_FILE_ID = "res_file_id";
	/** :  res_thumb */
	public static final String RES_THUMB = "res_thumb";
	/** :  res_times */
	public static final String RES_TIMES = "res_times";
	/** :  res_length */
	public static final String RES_LENGTH = "res_length";
	/** :  res_open */
	public static final String RES_OPEN = "res_open";
	/** :  res_types */
	public static final String RES_TYPES = "res_types";
	/** :  res_state */
	public static final String RES_STATE = "res_state";
	/** :  res_user_id */
	public static final String RES_USER_ID = "res_user_id";
	/** :  res_create */
	public static final String RES_CREATE = "res_create";
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
	private int types;
	/**  */
	private int state;
	/**  */
	private long userId;
	/**  */
	private Date create;
	public long getId() { 
		return this.id;
	} 
	@Column(value="res_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getName() { 
		return this.name;
	} 
	@Column(value="res_name")
	public void setName(String name) { 
		this.name = name;
	} 
	public String getDescribe() { 
		return this.describe;
	} 
	@Column(value="res_describe")
	public void setDescribe(String describe) { 
		this.describe = describe;
	} 
	public String getLable() { 
		return this.lable;
	} 
	@Column(value="res_lable")
	public void setLable(String lable) { 
		this.lable = lable;
	} 
	public long getFileId() { 
		return this.fileId;
	} 
	@Column(value="res_file_id")
	public void setFileId(long fileId) { 
		this.fileId = fileId;
	} 
	public String getThumb() { 
		return this.thumb;
	} 
	@Column(value="res_thumb")
	public void setThumb(String thumb) { 
		this.thumb = thumb;
	} 
	public long getTimes() { 
		return this.times;
	} 
	@Column(value="res_times")
	public void setTimes(long times) { 
		this.times = times;
	} 
	public long getLength() { 
		return this.length;
	} 
	@Column(value="res_length")
	public void setLength(long length) { 
		this.length = length;
	} 
	public int getOpen() { 
		return this.open;
	} 
	@Column(value="res_open")
	public void setOpen(int open) { 
		this.open = open;
	} 
	public int getTypes() { 
		return this.types;
	} 
	@Column(value="res_types")
	public void setTypes(int types) { 
		this.types = types;
	} 
	public int getState() { 
		return this.state;
	} 
	@Column(value="res_state")
	public void setState(int state) { 
		this.state = state;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="res_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public Date getCreate() { 
		return this.create;
	} 
	@Column(value="res_create")
	public void setCreate(Date create) { 
		this.create = create;
	} 
	public static int insert(IDao dao, long id, String name, String describe, String lable, long fileId, String thumb, long times, long length, int open, int types, int state, long userId, Date create) {
		return dao.insert(TABLE_NAME, new String[]{RES_ID, RES_NAME, RES_DESCRIBE, RES_LABLE, RES_FILE_ID, RES_THUMB, RES_TIMES, RES_LENGTH, RES_OPEN, RES_TYPES, RES_STATE, RES_USER_ID, RES_CREATE}, id, name, describe, lable, fileId, thumb, times, length, open, types, state, userId, create);
	}
	

	public static int updateById(IDao dao, long id, String name, String describe, String lable, long fileId, String thumb, long times, long length, int open, int types, int state, long userId, Date create) {
		return dao.update(TABLE_NAME, new String[]{RES_ID, RES_NAME, RES_DESCRIBE, RES_LABLE, RES_FILE_ID, RES_THUMB, RES_TIMES, RES_LENGTH, RES_OPEN, RES_TYPES, RES_STATE, RES_USER_ID, RES_CREATE}, new String[]{RES_ID}, id, name, describe, lable, fileId, thumb, times, length, open, types, state, userId, create, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{RES_ID}, id);
	}
	

	public static ResourceInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, RES_ID, RES_NAME, RES_DESCRIBE, RES_LABLE, RES_FILE_ID, RES_THUMB, RES_TIMES, RES_LENGTH, RES_OPEN, RES_TYPES, RES_STATE, RES_USER_ID, RES_CREATE).whereTrue();
		sql.andEq(RES_ID).params(id);
		return dao.queryOne(ResourceInfo.class, sql);
	}
	

	public static List<ResourceInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, RES_ID, RES_NAME, RES_DESCRIBE, RES_LABLE, RES_FILE_ID, RES_THUMB, RES_TIMES, RES_LENGTH, RES_OPEN, RES_TYPES, RES_STATE, RES_USER_ID, RES_CREATE).whereTrue();
		return dao.query(ResourceInfo.class, sql);
	}
	

	public static List<ResourceInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, RES_ID, RES_NAME, RES_DESCRIBE, RES_LABLE, RES_FILE_ID, RES_THUMB, RES_TIMES, RES_LENGTH, RES_OPEN, RES_TYPES, RES_STATE, RES_USER_ID, RES_CREATE).whereTrue();
		return dao.query(paging, ResourceInfo.class, sql);
	}
	

} 