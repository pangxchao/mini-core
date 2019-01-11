package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
import java.util.Date;
@Table("saas_gen_info")
public class SaasGenInfo implements IDaoModel<SaasGenInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : saas_gen_info */
	public static final String TABLE_NAME = "saas_gen_info";
	/** :  saas_gen_id */
	public static final String SAAS_GEN_ID = "saas_gen_id";
	/** :  saas_gen_number */
	public static final String SAAS_GEN_NUMBER = "saas_gen_number";
	/** :  saas_gen_create_time */
	public static final String SAAS_GEN_CREATE_TIME = "saas_gen_create_time";
	/**  */
	private long id;
	/**  */
	private long number;
	/**  */
	private Date createTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="saas_gen_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getNumber() { 
		return this.number;
	} 
	@Column(value="saas_gen_number")
	public void setNumber(long number) { 
		this.number = number;
	} 
	public Date getCreateTime() { 
		return this.createTime;
	} 
	@Column(value="saas_gen_create_time")
	public void setCreateTime(Date createTime) { 
		this.createTime = createTime;
	} 
	public static int insert(IDao dao, long id, long number, Date createTime) {
		return dao.insert(TABLE_NAME, new String[]{SAAS_GEN_ID, SAAS_GEN_NUMBER, SAAS_GEN_CREATE_TIME}, id, number, createTime);
	}
	

	public static int updateById(IDao dao, long id, long number, Date createTime) {
		return dao.update(TABLE_NAME, new String[]{SAAS_GEN_ID, SAAS_GEN_NUMBER, SAAS_GEN_CREATE_TIME}, new String[]{SAAS_GEN_ID}, id, number, createTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{SAAS_GEN_ID}, id);
	}
	

	public static SaasGenInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_GEN_ID, SAAS_GEN_NUMBER, SAAS_GEN_CREATE_TIME).whereTrue();
		sql.andEq(SAAS_GEN_ID).params(id);
		return dao.queryOne(SaasGenInfo.class, sql);
	}
	

	public static List<SaasGenInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_GEN_ID, SAAS_GEN_NUMBER, SAAS_GEN_CREATE_TIME).whereTrue();
		return dao.query(SaasGenInfo.class, sql);
	}
	

	public static List<SaasGenInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, SAAS_GEN_ID, SAAS_GEN_NUMBER, SAAS_GEN_CREATE_TIME).whereTrue();
		return dao.query(paging, SaasGenInfo.class, sql);
	}
	

} 