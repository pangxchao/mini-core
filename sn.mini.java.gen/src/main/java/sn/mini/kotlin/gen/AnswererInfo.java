package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
@Table("answerer_info")
public class AnswererInfo implements IDaoModel<AnswererInfo>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : answerer_info */
	public static final String TABLE_NAME = "answerer_info";
	/** 答题ID:  answer_id */
	public static final String ANSWER_ID = "answer_id";
	/** 答题用户:  answer_user_id */
	public static final String ANSWER_USER_ID = "answer_user_id";
	/** 知识ID:  answer_knowle_id */
	public static final String ANSWER_KNOWLE_ID = "answer_knowle_id";
	/** 知识圈ID:  answer_coterie_id */
	public static final String ANSWER_COTERIE_ID = "answer_coterie_id";
	/** 题号:  answer_number */
	public static final String ANSWER_NUMBER = "answer_number";
	/** 是否正确，0：否，1：是:  answer_is_right */
	public static final String ANSWER_IS_RIGHT = "answer_is_right";
	/** 是否为客观题:  answer_objective */
	public static final String ANSWER_OBJECTIVE = "answer_objective";
	/** 答题内容:  answer_content */
	public static final String ANSWER_CONTENT = "answer_content";
	/** 答题时间:  answer_time */
	public static final String ANSWER_TIME = "answer_time";
	/** 答题ID */
	private long id;
	/** 答题用户 */
	private long userId;
	/** 知识ID */
	private long knowleId;
	/** 知识圈ID */
	private long coterieId;
	/** 题号 */
	private int number;
	/** 是否正确，0：否，1：是 */
	private int isRight;
	/** 是否为客观题 */
	private int objective;
	/** 答题内容 */
	private String content;
	/** 答题时间 */
	private long time;
	public long getId() { 
		return this.id;
	} 
	@Column(value="answer_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="answer_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getKnowleId() { 
		return this.knowleId;
	} 
	@Column(value="answer_knowle_id")
	public void setKnowleId(long knowleId) { 
		this.knowleId = knowleId;
	} 
	public long getCoterieId() { 
		return this.coterieId;
	} 
	@Column(value="answer_coterie_id")
	public void setCoterieId(long coterieId) { 
		this.coterieId = coterieId;
	} 
	public int getNumber() { 
		return this.number;
	} 
	@Column(value="answer_number")
	public void setNumber(int number) { 
		this.number = number;
	} 
	public int getIsRight() { 
		return this.isRight;
	} 
	@Column(value="answer_is_right")
	public void setIsRight(int isRight) { 
		this.isRight = isRight;
	} 
	public int getObjective() { 
		return this.objective;
	} 
	@Column(value="answer_objective")
	public void setObjective(int objective) { 
		this.objective = objective;
	} 
	public String getContent() { 
		return this.content;
	} 
	@Column(value="answer_content")
	public void setContent(String content) { 
		this.content = content;
	} 
	public long getTime() { 
		return this.time;
	} 
	@Column(value="answer_time")
	public void setTime(long time) { 
		this.time = time;
	} 
	public static int insert(IDao dao, long id, long userId, long knowleId, long coterieId, int number, int isRight, int objective, String content, long time) {
		return dao.insert(TABLE_NAME, new String[]{ANSWER_ID, ANSWER_USER_ID, ANSWER_KNOWLE_ID, ANSWER_COTERIE_ID, ANSWER_NUMBER, ANSWER_IS_RIGHT, ANSWER_OBJECTIVE, ANSWER_CONTENT, ANSWER_TIME}, id, userId, knowleId, coterieId, number, isRight, objective, content, time);
	}
	

	public static int updateById(IDao dao, long id, long userId, long knowleId, long coterieId, int number, int isRight, int objective, String content, long time) {
		return dao.update(TABLE_NAME, new String[]{ANSWER_ID, ANSWER_USER_ID, ANSWER_KNOWLE_ID, ANSWER_COTERIE_ID, ANSWER_NUMBER, ANSWER_IS_RIGHT, ANSWER_OBJECTIVE, ANSWER_CONTENT, ANSWER_TIME}, new String[]{ANSWER_ID}, id, userId, knowleId, coterieId, number, isRight, objective, content, time, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{ANSWER_ID}, id);
	}
	

	public static AnswererInfo findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, ANSWER_ID, ANSWER_USER_ID, ANSWER_KNOWLE_ID, ANSWER_COTERIE_ID, ANSWER_NUMBER, ANSWER_IS_RIGHT, ANSWER_OBJECTIVE, ANSWER_CONTENT, ANSWER_TIME).whereTrue();
		sql.andEq(ANSWER_ID).params(id);
		return dao.queryOne(AnswererInfo.class, sql);
	}
	

	public static List<AnswererInfo> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, ANSWER_ID, ANSWER_USER_ID, ANSWER_KNOWLE_ID, ANSWER_COTERIE_ID, ANSWER_NUMBER, ANSWER_IS_RIGHT, ANSWER_OBJECTIVE, ANSWER_CONTENT, ANSWER_TIME).whereTrue();
		return dao.query(AnswererInfo.class, sql);
	}
	

	public static List<AnswererInfo> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, ANSWER_ID, ANSWER_USER_ID, ANSWER_KNOWLE_ID, ANSWER_COTERIE_ID, ANSWER_NUMBER, ANSWER_IS_RIGHT, ANSWER_OBJECTIVE, ANSWER_CONTENT, ANSWER_TIME).whereTrue();
		return dao.query(paging, AnswererInfo.class, sql);
	}
	

} 