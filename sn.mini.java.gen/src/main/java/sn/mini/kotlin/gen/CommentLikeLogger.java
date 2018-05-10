package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
@Table("comment_like_logger")
public class CommentLikeLogger implements IDaoModel<CommentLikeLogger>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : comment_like_logger */
	public static final String TABLE_NAME = "comment_like_logger";
	/** 点赞ID:  like_id */
	public static final String LIKE_ID = "like_id";
	/** 评论ID:  like_comment_id */
	public static final String LIKE_COMMENT_ID = "like_comment_id";
	/** 知识ID:  like_knowle_id */
	public static final String LIKE_KNOWLE_ID = "like_knowle_id";
	/** 知识圈ID:  like_coterie_id */
	public static final String LIKE_COTERIE_ID = "like_coterie_id";
	/** 投票用户ID:  like_user_id */
	public static final String LIKE_USER_ID = "like_user_id";
	/** 投票时间:  like_create_time */
	public static final String LIKE_CREATE_TIME = "like_create_time";
	/** 点赞ID */
	private long id;
	/** 评论ID */
	private long commentId;
	/** 知识ID */
	private long knowleId;
	/** 知识圈ID */
	private long coterieId;
	/** 投票用户ID */
	private long userId;
	/** 投票时间 */
	private long createTime;
	public long getId() { 
		return this.id;
	} 
	@Column(value="like_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getCommentId() { 
		return this.commentId;
	} 
	@Column(value="like_comment_id")
	public void setCommentId(long commentId) { 
		this.commentId = commentId;
	} 
	public long getKnowleId() { 
		return this.knowleId;
	} 
	@Column(value="like_knowle_id")
	public void setKnowleId(long knowleId) { 
		this.knowleId = knowleId;
	} 
	public long getCoterieId() { 
		return this.coterieId;
	} 
	@Column(value="like_coterie_id")
	public void setCoterieId(long coterieId) { 
		this.coterieId = coterieId;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="like_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getCreateTime() { 
		return this.createTime;
	} 
	@Column(value="like_create_time")
	public void setCreateTime(long createTime) { 
		this.createTime = createTime;
	} 
	public static int insert(IDao dao, long id, long commentId, long knowleId, long coterieId, long userId, long createTime) {
		return dao.insert(TABLE_NAME, new String[]{LIKE_ID, LIKE_COMMENT_ID, LIKE_KNOWLE_ID, LIKE_COTERIE_ID, LIKE_USER_ID, LIKE_CREATE_TIME}, id, commentId, knowleId, coterieId, userId, createTime);
	}
	

	public static int updateById(IDao dao, long id, long commentId, long knowleId, long coterieId, long userId, long createTime) {
		return dao.update(TABLE_NAME, new String[]{LIKE_ID, LIKE_COMMENT_ID, LIKE_KNOWLE_ID, LIKE_COTERIE_ID, LIKE_USER_ID, LIKE_CREATE_TIME}, new String[]{LIKE_ID}, id, commentId, knowleId, coterieId, userId, createTime, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{LIKE_ID}, id);
	}
	

	public static CommentLikeLogger findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, LIKE_ID, LIKE_COMMENT_ID, LIKE_KNOWLE_ID, LIKE_COTERIE_ID, LIKE_USER_ID, LIKE_CREATE_TIME).whereTrue();
		sql.andEq(LIKE_ID).params(id);
		return dao.queryOne(CommentLikeLogger.class, sql);
	}
	

	public static List<CommentLikeLogger> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, LIKE_ID, LIKE_COMMENT_ID, LIKE_KNOWLE_ID, LIKE_COTERIE_ID, LIKE_USER_ID, LIKE_CREATE_TIME).whereTrue();
		return dao.query(CommentLikeLogger.class, sql);
	}
	

	public static List<CommentLikeLogger> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, LIKE_ID, LIKE_COMMENT_ID, LIKE_KNOWLE_ID, LIKE_COTERIE_ID, LIKE_USER_ID, LIKE_CREATE_TIME).whereTrue();
		return dao.query(paging, CommentLikeLogger.class, sql);
	}
	

} 