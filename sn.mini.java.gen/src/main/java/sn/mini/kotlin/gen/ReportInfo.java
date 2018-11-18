package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("report_info")
public class ReportInfo implements IDaoModel<ReportInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : report_info */
	public static final String TABLE_NAME = "report_info";
	/** :  report_id */
	public static final String REPORT_ID = "report_id";
	/** 举报目标ID:  report_target_id */
	public static final String REPORT_TARGET_ID = "report_target_id";
	/** 举报对象0-知识圈, 1:tdy:  report_type */
	public static final String REPORT_TYPE = "report_type";
	/** 举报描述:  report_describe */
	public static final String REPORT_DESCRIBE = "report_describe";
	/** 附件上传云节点ID:  report_cloud_id */
	public static final String REPORT_CLOUD_ID = "report_cloud_id";
	/** 举报图片-选填，可多选:  report_image_url */
	public static final String REPORT_IMAGE_URL = "report_image_url";
	/** 联系方式:  report_link_list */
	public static final String REPORT_LINK_LIST = "report_link_list";
	/** 举报时间:  report_time */
	public static final String REPORT_TIME = "report_time";
	/**  */
	private long id;
	/** 举报目标ID */
	private long targetId;
	/** 举报对象0-知识圈, 1:tdy */
	private int type;
	/** 举报描述 */
	private String describe;
	/** 附件上传云节点ID */
	private long cloudId;
	/** 举报图片-选填，可多选 */
	private String imageUrl;
	/** 联系方式 */
	private String linkList;
	/** 举报时间 */
	private Date time;
	public long getId() { 
		return this.id;
	} 
	@Column(value="report_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getTargetId() { 
		return this.targetId;
	} 
	@Column(value="report_target_id")
	public void setTargetId(long targetId) { 
		this.targetId = targetId;
	} 
	public int getType() { 
		return this.type;
	} 
	@Column(value="report_type")
	public void setType(int type) { 
		this.type = type;
	} 
	public String getDescribe() { 
		return this.describe;
	} 
	@Column(value="report_describe")
	public void setDescribe(String describe) { 
		this.describe = describe;
	} 
	public long getCloudId() { 
		return this.cloudId;
	} 
	@Column(value="report_cloud_id")
	public void setCloudId(long cloudId) { 
		this.cloudId = cloudId;
	} 
	public String getImageUrl() { 
		return this.imageUrl;
	} 
	@Column(value="report_image_url")
	public void setImageUrl(String imageUrl) { 
		this.imageUrl = imageUrl;
	} 
	public String getLinkList() { 
		return this.linkList;
	} 
	@Column(value="report_link_list")
	public void setLinkList(String linkList) { 
		this.linkList = linkList;
	} 
	public Date getTime() { 
		return this.time;
	} 
	@Column(value="report_time")
	public void setTime(Date time) { 
		this.time = time;
	} 
	public static int insert(IDao dao, long id, long targetId, int type, String describe, long cloudId, String imageUrl, String linkList, Date time) {
		return dao.insert(TABLE_NAME, new String[]{REPORT_ID, REPORT_TARGET_ID, REPORT_TYPE, REPORT_DESCRIBE, REPORT_CLOUD_ID, REPORT_IMAGE_URL, REPORT_LINK_LIST, REPORT_TIME}, id, targetId, type, describe, cloudId, imageUrl, linkList, time);
	}
	

	public static int updateById(IDao dao, long id, long targetId, int type, String describe, long cloudId, String imageUrl, String linkList, Date time) {
		return dao.update(TABLE_NAME, new String[]{REPORT_ID, REPORT_TARGET_ID, REPORT_TYPE, REPORT_DESCRIBE, REPORT_CLOUD_ID, REPORT_IMAGE_URL, REPORT_LINK_LIST, REPORT_TIME}, new String[]{REPORT_ID}, id, targetId, type, describe, cloudId, imageUrl, linkList, time, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{REPORT_ID}, id);
	}
	

	public static ReportInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, REPORT_ID, REPORT_TARGET_ID, REPORT_TYPE, REPORT_DESCRIBE, REPORT_CLOUD_ID, REPORT_IMAGE_URL, REPORT_LINK_LIST, REPORT_TIME).whereTrue();
		sql.andEq(REPORT_ID).params(id);
		return dao.queryOne(ReportInfo.class, sql);
	}
	

	public static List<ReportInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, REPORT_ID, REPORT_TARGET_ID, REPORT_TYPE, REPORT_DESCRIBE, REPORT_CLOUD_ID, REPORT_IMAGE_URL, REPORT_LINK_LIST, REPORT_TIME).whereTrue();
		return dao.query(ReportInfo.class, sql);
	}
	

	public static List<ReportInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, REPORT_ID, REPORT_TARGET_ID, REPORT_TYPE, REPORT_DESCRIBE, REPORT_CLOUD_ID, REPORT_IMAGE_URL, REPORT_LINK_LIST, REPORT_TIME).whereTrue();
		return dao.query(paging, ReportInfo.class, sql);
	}
	

} 