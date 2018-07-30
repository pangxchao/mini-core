package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
@Table("cloud_info")
public class CloudInfo implements IDaoModel<CloudInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : cloud_info */
	public static final String TABLE_NAME = "cloud_info";
	/** 云节点ID:  cloud_id */
	public static final String CLOUD_ID = "cloud_id";
	/** 云节点名称:  cloud_name */
	public static final String CLOUD_NAME = "cloud_name";
	/** 云节点域名:  cloud_host */
	public static final String CLOUD_HOST = "cloud_host";
	/** :  cloud_host_cdn */
	public static final String CLOUD_HOST_CDN = "cloud_host_cdn";
	/** 云节点验证字符串:  cloud_code */
	public static final String CLOUD_CODE = "cloud_code";
	/** 云节点类型。 0；系统云，1：蒙以云，2：其它云:  cloud_type */
	public static final String CLOUD_TYPE = "cloud_type";
	/** 云节点ID */
	private long id;
	/** 云节点名称 */
	private String name;
	/** 云节点域名 */
	private String host;
	/**  */
	private String hostCdn;
	/** 云节点验证字符串 */
	private String code;
	/** 云节点类型。 0；系统云，1：蒙以云，2：其它云 */
	private int type;
	public long getId() { 
		return this.id;
	} 
	@Column(value="cloud_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getName() { 
		return this.name;
	} 
	@Column(value="cloud_name")
	public void setName(String name) { 
		this.name = name;
	} 
	public String getHost() { 
		return this.host;
	} 
	@Column(value="cloud_host")
	public void setHost(String host) { 
		this.host = host;
	} 
	public String getHostCdn() { 
		return this.hostCdn;
	} 
	@Column(value="cloud_host_cdn")
	public void setHostCdn(String hostCdn) { 
		this.hostCdn = hostCdn;
	} 
	public String getCode() { 
		return this.code;
	} 
	@Column(value="cloud_code")
	public void setCode(String code) { 
		this.code = code;
	} 
	public int getType() { 
		return this.type;
	} 
	@Column(value="cloud_type")
	public void setType(int type) { 
		this.type = type;
	} 
	public static int insert(IDao dao, long id, String name, String host, String hostCdn, String code, int type) {
		return dao.insert(TABLE_NAME, new String[]{CLOUD_ID, CLOUD_NAME, CLOUD_HOST, CLOUD_HOST_CDN, CLOUD_CODE, CLOUD_TYPE}, id, name, host, hostCdn, code, type);
	}
	

	public static int updateById(IDao dao, long id, String name, String host, String hostCdn, String code, int type) {
		return dao.update(TABLE_NAME, new String[]{CLOUD_ID, CLOUD_NAME, CLOUD_HOST, CLOUD_HOST_CDN, CLOUD_CODE, CLOUD_TYPE}, new String[]{CLOUD_ID}, id, name, host, hostCdn, code, type, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{CLOUD_ID}, id);
	}
	

	public static CloudInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, CLOUD_ID, CLOUD_NAME, CLOUD_HOST, CLOUD_HOST_CDN, CLOUD_CODE, CLOUD_TYPE).whereTrue();
		sql.andEq(CLOUD_ID).params(id);
		return dao.queryOne(CloudInfo.class, sql);
	}
	

	public static List<CloudInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, CLOUD_ID, CLOUD_NAME, CLOUD_HOST, CLOUD_HOST_CDN, CLOUD_CODE, CLOUD_TYPE).whereTrue();
		return dao.query(CloudInfo.class, sql);
	}
	

	public static List<CloudInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, CLOUD_ID, CLOUD_NAME, CLOUD_HOST, CLOUD_HOST_CDN, CLOUD_CODE, CLOUD_TYPE).whereTrue();
		return dao.query(paging, CloudInfo.class, sql);
	}
	

} 