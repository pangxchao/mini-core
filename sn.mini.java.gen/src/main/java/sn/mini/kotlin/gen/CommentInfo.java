package sn.mini.kotlin.gen;


import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;

import java.util.List;
@Table("comment_info")
public class CommentInfo implements IDaoModel<CommentInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : comment_info */
	public static final String TABLE_NAME = "comment_info";
	/** 评论ID:  comment_id */
	public static final String COMMENT_ID = "comment_id";
	/** 评论内容:  comment_content */
	public static final String COMMENT_CONTENT = "comment_content";
	/** 点赞数:  comment_good_count */
	public static final String COMMENT_GOOD_COUNT = "comment_good_count";
	/** 评论状态,0:未审核， 1：已审核:  comment_state */
	public static final String COMMENT_STATE = "comment_state";
	/** 知识ID:  comment_knowle_id */
	public static final String COMMENT_KNOWLE_ID = "comment_knowle_id";
	/** 知识圈ID:  comment_coterie_id */
	public static final String COMMENT_COTERIE_ID = "comment_coterie_id";
	/** 评论用户ID:  comment_user_id */
	public static final String COMMENT_USER_ID = "comment_user_id";
	/** 评论时间:  comment_create_time */
	public static final String COMMENT_CREATE_TIME = "comment_create_time";
	/** 0:正常，1：删除:  comment_is_delete */
	public static final String COMMENT_IS_DELETE = "comment_is_delete";
	/** 评论ID */
	private long id;
	/** 评论内容 */
	private String content;
	/** 点赞数 */
	private int goodCount;
	/** 评论状态,0:未审核， 1：已审核 */
	private int state;
	/** 知识ID */
	private long knowleId;
	/** 知识圈ID */
	private long coterieId;
	/** 评论用户ID */
	private long userId;
	/** 评论时间 */
	private long createTime;
	/** 0:正常，1：删除 */
	private int isDelete;
	public long getId() { 
		return this.id;
	} 
	@Column(value="comment_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public String getContent() { 
		return this.content;
	} 
	@Column(value="comment_content")
	public void setContent(String content) { 
		this.content = content;
	} 
	public int getGoodCount() { 
		return this.goodCount;
	} 
	@Column(value="comment_good_count")
	public void setGoodCount(int goodCount) { 
		this.goodCount = goodCount;
	} 
	public int getState() { 
		return this.state;
	} 
	@Column(value="comment_state")
	public void setState(int state) { 
		this.state = state;
	} 
	public long getKnowleId() { 
		return this.knowleId;
	} 
	@Column(value="comment_knowle_id")
	public void setKnowleId(long knowleId) { 
		this.knowleId = knowleId;
	} 
	public long getCoterieId() { 
		return this.coterieId;
	} 
	@Column(value="comment_coterie_id")
	public void setCoterieId(long coterieId) { 
		this.coterieId = coterieId;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="comment_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getCreateTime() { 
		return this.createTime;
	} 
	@Column(value="comment_create_time")
	public void setCreateTime(long createTime) { 
		this.createTime = createTime;
	} 
	public int getIsDelete() { 
		return this.isDelete;
	} 
	@Column(value="comment_is_delete")
	public void setIsDelete(int isDelete) { 
		this.isDelete = isDelete;
	} 
	public static int insert(IDao dao, long id, String content, int goodCount, int state, long knowleId, long coterieId, long userId, long createTime, int isDelete) {
		return dao.insert(TABLE_NAME, new String[]{COMMENT_ID, COMMENT_CONTENT, COMMENT_GOOD_COUNT, COMMENT_STATE, COMMENT_KNOWLE_ID, COMMENT_COTERIE_ID, COMMENT_USER_ID, COMMENT_CREATE_TIME, COMMENT_IS_DELETE}, id, content, goodCount, state, knowleId, coterieId, userId, createTime, isDelete);
	}
	

	public static int updateById(IDao dao, long id, String content, int goodCount, int state, long knowleId, long coterieId, long userId, long createTime, int isDelete) {
		return dao.update(TABLE_NAME, new String[]{COMMENT_ID, COMMENT_CONTENT, COMMENT_GOOD_COUNT, COMMENT_STATE, COMMENT_KNOWLE_ID, COMMENT_COTERIE_ID, COMMENT_USER_ID, COMMENT_CREATE_TIME, COMMENT_IS_DELETE}, new String[]{COMMENT_ID}, id, content, goodCount, state, knowleId, coterieId, userId, createTime, isDelete, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{COMMENT_ID}, id);
	}
	

	public static CommentInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, COMMENT_ID, COMMENT_CONTENT, COMMENT_GOOD_COUNT, COMMENT_STATE, COMMENT_KNOWLE_ID, COMMENT_COTERIE_ID, COMMENT_USER_ID, COMMENT_CREATE_TIME, COMMENT_IS_DELETE).whereTrue();
		sql.andEq(COMMENT_ID).params(id);
		return dao.queryOne(CommentInfo.class, sql);
	}
	

	public static List<CommentInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, COMMENT_ID, COMMENT_CONTENT, COMMENT_GOOD_COUNT, COMMENT_STATE, COMMENT_KNOWLE_ID, COMMENT_COTERIE_ID, COMMENT_USER_ID, COMMENT_CREATE_TIME, COMMENT_IS_DELETE).whereTrue();
		return dao.query(CommentInfo.class, sql);
	}
	

	public static List<CommentInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, COMMENT_ID, COMMENT_CONTENT, COMMENT_GOOD_COUNT, COMMENT_STATE, COMMENT_KNOWLE_ID, COMMENT_COTERIE_ID, COMMENT_USER_ID, COMMENT_CREATE_TIME, COMMENT_IS_DELETE).whereTrue();
		return dao.query(paging, CommentInfo.class, sql);
	}
	

} 