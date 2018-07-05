package sn.mini.kotlin.gen;


import java.util.List;
import sn.mini.java.jdbc.Sql;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.annotaion.Table;
import sn.mini.java.jdbc.model.IDaoModel;
@Table("coterie_member")
public class CoterieMember implements IDaoModel<CoterieMember>{ 
	private static final long serialVersionUID = 1L;
	/** 表名称 : coterie_member */
	public static final String TABLE_NAME = "coterie_member";
	/** 知识圈成员ID:  member_id */
	public static final String MEMBER_ID = "member_id";
	/** 知识圈成员用户ID:  member_user_id */
	public static final String MEMBER_USER_ID = "member_user_id";
	/** 知识圈成员知识圈ID:  member_coterie_id */
	public static final String MEMBER_COTERIE_ID = "member_coterie_id";
	/** 身份。 0：粉丝，1：成员，2：管理员，3：拥有者，4：黑名单:  member_identity */
	public static final String MEMBER_IDENTITY = "member_identity";
	/** 未读消息数:  member_unread_count */
	public static final String MEMBER_UNREAD_COUNT = "member_unread_count";
	/** 有将期（最后到期时间）:  member_expired_time */
	public static final String MEMBER_EXPIRED_TIME = "member_expired_time";
	/** 消息免打扰标识:  member_un_disturb */
	public static final String MEMBER_UN_DISTURB = "member_un_disturb";
	/** 知识圈成员ID */
	private long id;
	/** 知识圈成员用户ID */
	private long userId;
	/** 知识圈成员知识圈ID */
	private long coterieId;
	/** 身份。 0：粉丝，1：成员，2：管理员，3：拥有者，4：黑名单 */
	private int identity;
	/** 未读消息数 */
	private int unreadCount;
	/** 有将期（最后到期时间） */
	private long expiredTime;
	/** 消息免打扰标识 */
	private int unDisturb;
	public long getId() { 
		return this.id;
	} 
	@Column(value="member_id", des = 2)
	public void setId(long id) { 
		this.id = id;
	} 
	public long getUserId() { 
		return this.userId;
	} 
	@Column(value="member_user_id")
	public void setUserId(long userId) { 
		this.userId = userId;
	} 
	public long getCoterieId() { 
		return this.coterieId;
	} 
	@Column(value="member_coterie_id")
	public void setCoterieId(long coterieId) { 
		this.coterieId = coterieId;
	} 
	public int getIdentity() { 
		return this.identity;
	} 
	@Column(value="member_identity")
	public void setIdentity(int identity) { 
		this.identity = identity;
	} 
	public int getUnreadCount() { 
		return this.unreadCount;
	} 
	@Column(value="member_unread_count")
	public void setUnreadCount(int unreadCount) { 
		this.unreadCount = unreadCount;
	} 
	public long getExpiredTime() { 
		return this.expiredTime;
	} 
	@Column(value="member_expired_time")
	public void setExpiredTime(long expiredTime) { 
		this.expiredTime = expiredTime;
	} 
	public int getUnDisturb() { 
		return this.unDisturb;
	} 
	@Column(value="member_un_disturb")
	public void setUnDisturb(int unDisturb) { 
		this.unDisturb = unDisturb;
	} 
	public static int insert(IDao dao, long id, long userId, long coterieId, int identity, int unreadCount, long expiredTime, int unDisturb) {
		return dao.insert(TABLE_NAME, new String[]{MEMBER_ID, MEMBER_USER_ID, MEMBER_COTERIE_ID, MEMBER_IDENTITY, MEMBER_UNREAD_COUNT, MEMBER_EXPIRED_TIME, MEMBER_UN_DISTURB}, id, userId, coterieId, identity, unreadCount, expiredTime, unDisturb);
	}
	

	public static int updateById(IDao dao, long id, long userId, long coterieId, int identity, int unreadCount, long expiredTime, int unDisturb) {
		return dao.update(TABLE_NAME, new String[]{MEMBER_ID, MEMBER_USER_ID, MEMBER_COTERIE_ID, MEMBER_IDENTITY, MEMBER_UNREAD_COUNT, MEMBER_EXPIRED_TIME, MEMBER_UN_DISTURB}, new String[]{MEMBER_ID}, id, userId, coterieId, identity, unreadCount, expiredTime, unDisturb, id);
	}
	

	public static int deleteById(IDao dao, long id) {
		return dao.delete(TABLE_NAME, new String[]{MEMBER_ID}, id);
	}
	

	public static CoterieMember findById(IDao dao, long id) {
		Sql sql = Sql.createSelect(TABLE_NAME, MEMBER_ID, MEMBER_USER_ID, MEMBER_COTERIE_ID, MEMBER_IDENTITY, MEMBER_UNREAD_COUNT, MEMBER_EXPIRED_TIME, MEMBER_UN_DISTURB).whereTrue();
		sql.andEq(MEMBER_ID).params(id);
		return dao.queryOne(CoterieMember.class, sql);
	}
	

	public static List<CoterieMember> find(IDao dao) {
		Sql sql = Sql.createSelect(TABLE_NAME, MEMBER_ID, MEMBER_USER_ID, MEMBER_COTERIE_ID, MEMBER_IDENTITY, MEMBER_UNREAD_COUNT, MEMBER_EXPIRED_TIME, MEMBER_UN_DISTURB).whereTrue();
		return dao.query(CoterieMember.class, sql);
	}
	

	public static List<CoterieMember> find(Paging paging, IDao dao){
		Sql sql = Sql.createSelect(TABLE_NAME, MEMBER_ID, MEMBER_USER_ID, MEMBER_COTERIE_ID, MEMBER_IDENTITY, MEMBER_UNREAD_COUNT, MEMBER_EXPIRED_TIME, MEMBER_UN_DISTURB).whereTrue();
		return dao.query(paging, CoterieMember.class, sql);
	}
	

} 