/**
 * Created the sn.mini.dao.Sql.java
 * @created 2016年9月22日 下午3:28:42
 * @version 1.0.0
 */
package sn.mini.kotlin.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sn.mini.java.jdbc.mapper.cell.ICell;
import sn.mini.java.util.lang.StringUtil;

/**
 * sn.mini.dao.Sql.java
 * @author XChao
 */
public final class Sql {

	private StringBuilder content = new StringBuilder();
	private List<Object> params = new ArrayList<>();
	private Map<String, ICell<?>> cells = new HashMap<String, ICell<?>>();

	/**
	 * 设置构造方法为私有，让外部创建当前对象时都调用静态方法创建
	 * @return
	 */
	private Sql() {
	}

	/**
	 * Sql语句
	 * @param sql
	 * @return
	 */
	public Sql sql(String sql) {
		this.content.append(sql);
		return this;
	}

	/**
	 * insert into
	 * @return
	 */
	public Sql insert() {
		return this.sql("insert into ");
	}

	/**
	 * insert into tname
	 * @param tname
	 * @return
	 */
	public Sql insert(String tname) {
		return this.sql("insert into ").sql(tname).sql(" ");
	}

	/**
	 * replace into
	 * @return
	 */
	public Sql replace() {
		return this.sql("replace into ");
	}

	/**
	 * replace into tname
	 * @param tname
	 * @return
	 */
	public Sql replace(String tname) {
		return this.sql("replace into ").sql(tname).sql(" ");
	}

	/**
	 * column1, column2 ...
	 * @param columns
	 * @return
	 */
	public Sql col(String... columns) {
		return this.sql(StringUtil.join(columns, ", "));
	}

	public Sql values() {
		return this.sql(" values ");
	}

	/**
	 * values(?,?,?...?) count个?
	 * @param count
	 * @return
	 */
	public Sql values(int count) {
		String[] values = new String[count];
		Arrays.fill(values, "?");
		return this.values().sql("(").col(values).sql(")");
	}

	/**
	 * delete 语句
	 * @return
	 */
	public Sql delete() {
		return this.sql(" delete ");
	}

	/**
	 * from
	 * @return
	 */
	public Sql from() {
		return this.sql(" from ");
	}

	/**
	 * from tname
	 * @param tname
	 * @return
	 */
	public Sql from(String tname) {
		return from().sql(tname).sql(" ");
	}

	/**
	 * delete from tname
	 * @param tname
	 * @return
	 */
	public Sql delete(String tname) {
		return this.delete().from(tname);
	}

	/**
	 * update tname
	 * @param tname
	 * @return
	 */
	public Sql update(String tname) {
		return this.sql(" update ").sql(tname).sql(" ");
	}

	/**
	 * set
	 * @return
	 */
	public Sql set() {
		return this.sql(" set ");
	}

	/**
	 * column1 = ?, column2 = ?,...columnn = ?
	 * @param columns
	 * @return
	 */
	public Sql ucol(String... columns) {
		return this.sql(" ").sql(StringUtil.join(columns, " = ?, ")).sql(" = ? ");
	}

	/**
	 * select
	 * @return
	 */
	public Sql select() {
		return this.sql(" select ");
	}

	/**
	 * distinct
	 * @return
	 */
	public Sql distinct() {
		return this.sql(" distinct ");
	}

	/**
	 * as
	 * @return
	 */
	public Sql as() {
		return this.sql(" as ");
	}

	/**
	 * left
	 * @return
	 */
	public Sql left() {
		return this.sql(" left ");
	}

	/**
	 * right
	 * @return
	 */
	public Sql right() {
		return this.sql(" right ");
	}

	/**
	 * inner
	 * @return
	 */
	public Sql inner() {
		return this.sql(" inner ");
	}

	/**
	 * outer
	 * @return
	 */
	public Sql outer() {
		return this.sql(" outer ");
	}

	/**
	 * full
	 * @return
	 */
	public Sql full() {
		return this.sql(" full  ");
	}

	/**
	 * join joinName
	 * @param joinName
	 * @return
	 */
	public Sql join(String joinName) {
		return this.sql(" join ").sql(joinName).sql(" ");
	}

	/**
	 * on
	 * @return
	 */
	public Sql on() {
		return this.sql(" on ");
	}

	/**
	 * on col1 = col2
	 * @param col1
	 * @param col2
	 * @return
	 */
	public Sql on(String col1, String col2) {
		return this.on().sql(col1).sql(" = ").sql(col2).sql(" ");
	}

	/**
	 * where
	 * @return
	 */
	public Sql where() {
		return this.sql(" where ");
	}

	/**
	 * 1 = 2
	 * @return
	 */
	public Sql trues() {
		return this.sql(" 1 = 1 ");
	}

	/**
	 * 1 = 2
	 * @return
	 */
	public Sql falses() {
		return this.sql(" 1 = 2 ");
	}

	/**
	 * where 1 = 1
	 * @return
	 */
	public Sql whereTrue() {
		return this.where().sql(" 1 = 1 ");
	}

	/**
	 * where 1 = 2
	 * @return
	 */
	public Sql whereFalse() {
		return this.where().sql(" 1 = 2 ");
	}

	/**
	 * and
	 * @return
	 */
	public Sql and() {
		return this.sql(" and ");
	}

	/**
	 * or
	 * @return
	 */
	public Sql or() {
		return this.sql(" or ");
	}

	/**
	 * and col1 = ? and col2 = ?... and coln = ?
	 * @param cols
	 * @return
	 */
	public Sql andColEq(String... cols) {
		return this.and().sql(StringUtil.join(cols, " = ? and ")).sql(" = ? ");
	}

	/**
	 * or col1 = ? or col2 = ?... or coln = ?
	 * @param cols
	 * @return
	 */
	public Sql orColEq(String... cols) {
		return this.or().sql(StringUtil.join(cols, " = ? or ")).sql(" = ? ");
	}

	/**
	 * and col = ?
	 * @param col
	 * @return
	 */
	public Sql andEq(String col) {
		return this.and().sql(col).sql(" = ? ");
	}

	/**
	 * or col = ?
	 * @param col
	 * @return
	 */
	public Sql orEq(String col) {
		return this.or().sql(col).sql(" = ? ");
	}

	/**
	 * and col <> ?
	 * @param col
	 * @return
	 */
	public Sql andNotEq(String col) {
		return this.and().sql(col).sql(" <> ? ");
	}

	/**
	 * or col <> ?
	 * @param col
	 * @return
	 */
	public Sql orNotEq(String col) {
		return this.or().sql(col).sql(" <> ? ");
	}

	/**
	 * and col > ?
	 * @param col
	 * @return
	 */
	public Sql andGt(String col) {
		return this.and().sql(col).sql(" > ? ");
	}

	/**
	 * or col > ?
	 * @param col
	 * @return
	 */
	public Sql orGt(String col) {
		return this.or().sql(col).sql(" > ? ");
	}

	/**
	 * and col >= ?
	 * @param col
	 * @return
	 */
	public Sql andGtEq(String col) {
		return this.and().sql(col).sql(" >= ? ");
	}

	/**
	 * or col >= ?
	 * @param col
	 * @return
	 */
	public Sql orGtEq(String col) {
		return this.or().sql(col).sql(" >= ? ");
	}

	/**
	 * and col < ?
	 * @param col
	 * @return
	 */
	public Sql andLt(String col) {
		return this.and().sql(col).sql(" < ? ");
	}

	/**
	 * or col < ?
	 * @param col
	 * @return
	 */
	public Sql orLt(String col) {
		return this.or().sql(col).sql(" < ? ");
	}

	/**
	 * and col <= ?
	 * @param col
	 * @return
	 */
	public Sql andLtEq(String col) {
		return this.and().sql(col).sql(" <= ? ");
	}

	/**
	 * or col <= ?
	 * @param col
	 * @return
	 */
	public Sql orLtEq(String col) {
		return this.or().sql(col).sql(" <= ? ");
	}

	/**
	 * like
	 * @return
	 */
	public Sql like() {
		return this.sql(" like ");
	}

	/**
	 * and col like ?
	 * @param col
	 * @return
	 */
	public Sql andLike(String col) {
		return this.and().sql(col).like().sql(" ? ");
	}

	/**
	 * or col like ?
	 * @param col
	 * @return
	 */
	public Sql orLike(String col) {
		return this.or().sql(col).like().sql(" ? ");
	}

	/**
	 * MATCH(col1, col1...coln) AGAINST(? IN BOOLEAN MODE)
	 * @param col
	 * @return
	 */
	public Sql match(String... col) {
		return this.sql(" MATCH(").col(col).sql(") AGAINST(? IN BOOLEAN MODE)");
	}

	/**
	 * and MATCH(col1, col1...coln) AGAINST(? IN BOOLEAN MODE)
	 * @param col
	 * @return
	 */
	public Sql andMatch(String... col) {
		return this.and().match(col);
	}

	/**
	 * or MATCH(col1, col1...coln) AGAINST(? IN BOOLEAN MODE)
	 * @param col
	 * @return
	 */
	public Sql orMatch(String... col) {
		return this.or().match(col);
	}

	/**
	 * in
	 * @return
	 */
	public Sql in() {
		return this.sql(" in ");
	}

	/**
	 * group by
	 * @return
	 */
	public Sql groupBy() {
		return this.sql(" group by ");
	}

	/**
	 * group by col1, col2...coln
	 * @param col
	 * @return
	 */
	public Sql groupBy(String... col) {
		return this.groupBy().col(col);
	}

	/**
	 * order by
	 * @return
	 */
	public Sql orderBy() {
		return this.sql(" order by ");
	}

	/**
	 * order by col
	 * @param col
	 * @return
	 */
	public Sql orderBy(String col) {
		return this.orderBy().sql(col).sql(" ");
	}

	/**
	 * desc
	 * @return
	 */
	public Sql desc() {
		return this.sql(" desc ");
	}

	/**
	 * limit
	 * @return
	 */
	public Sql limit() {
		return this.sql(" limit ");
	}

	/**
	 * limit rows
	 * @param rows
	 * @return
	 */
	public Sql limit(int rows) {
		return this.limit().sql(" ").sql(String.valueOf(rows)).sql(" ");
	}

	/**
	 * limit start, rows
	 * @param start
	 * @param rows
	 * @return
	 */
	public Sql limit(int start, int rows) {
		return this.sql(" limit ").sql(" ").sql(String.valueOf(start)).sql(", ").sql(String.valueOf(rows)).sql(" ");
	}

	/**
	 * asc
	 * @return
	 */
	public Sql asc() {
		return this.sql(" asc ");
	}

	/**
	 * is
	 * @return
	 */
	public Sql is() {
		return this.sql(" is ");
	}

	/**
	 * not
	 * @return
	 */
	public Sql not() {
		return this.sql(" not ");
	}

	/**
	 * null
	 * @return
	 */
	public Sql nul1() {
		return this.sql(" null ");
	}

	/**
	 * equals ' = '
	 * @return
	 */
	public Sql eq() {
		return this.sql(" = ");
	}

	/**
	 * not equals ' <> '
	 * @return
	 */
	public Sql notEq() {
		return this.sql(" <> ");
	}

	/**
	 * Greater than ' > '
	 * @return
	 */
	public Sql gt() {
		return this.sql(" > ");
	}

	/**
	 * Less than ' < '
	 * @return
	 */
	public Sql lt() {
		return this.sql(" < ");
	}

	/**
	 * Greater than or equal ' >= '
	 * @return
	 */
	public Sql gtEq() {
		return this.sql(" >= ");
	}

	/**
	 * Less than or equal ' <= '
	 * @return
	 */
	public Sql ltEq() {
		return this.sql(" <= ");
	}

	/**
	 * Question mark ' ? '
	 * @return
	 */
	public Sql q() {
		return this.sql(" ? ");
	}

	/**
	 * comma ', '
	 * @return
	 */
	public Sql c() {
		return this.sql(", ");
	}

	/**
	 * Left bracket '('
	 * @return
	 */
	public Sql lb() {
		return this.sql("(");
	}

	/**
	 * Right bracket ') '
	 * @return
	 */
	public Sql rb() {
		return this.sql(") ");
	}

	/**
	 * plus ' + '
	 * @return
	 */
	public Sql plus() {
		return this.sql(" + ");
	}

	/**
	 * reduce ' - '
	 * @return
	 */
	public Sql reduce() {
		return this.sql(" - ");
	}

	/**
	 * asterisk ' * '
	 * @return
	 */
	public Sql ast() {
		return this.sql(" * ");
	}

	/**
	 * Slash ' / '
	 * @return
	 */
	public Sql slash() {
		return this.sql(" / ");
	}

	/**
	 * per cent ' % '
	 * @return
	 */
	public Sql percent() {
		return this.sql(" % ");
	}

	/**
	 * remainder ' % '
	 * @return
	 */
	public Sql rd() {
		return this.sql(" % ");
	}

	/**
	 * vertical line ' | '
	 * @return
	 */
	public Sql vl() {
		return this.sql(" | ");
	}

	/**
	 * bitwise and ' & '
	 * @return
	 */
	public Sql ba() {
		return this.sql(" & ");
	}

	/**
	 * XOR " ^ "
	 * @return
	 */
	public Sql xor() {
		return this.sql(" ^ ");
	}

	/**
	 * 位非(un) ' ~ '
	 * @return
	 */
	public Sql un() {
		return this.sql(" ~ ");
	}

	/**
	 * 向Sql中添加参数
	 * @param params
	 * @return
	 */
	public Sql params(Object... params) {
		for (Object param : params) {
			this.params.add(param);
		}
		return this;
	}

	/**
	 * 获取该sql中所有带?号参数的值列表数组
	 * @return The params
	 */
	public Object[] params() {
		return this.params.toArray();
	}

	public Sql cell(String name, ICell<?> cell) {
		this.cells.put(name, cell);
		return this;
	}

	/**
	 * 获取Sql中的ICell<?>实现
	 * @return The cells
	 */
	public Map<String, ICell<?>> cells() {
		return this.cells;
	}

	/**
	 * 获取该sql的完整sql字符串形式 return The content.toString()
	 */
	public String sqlString() {
		return this.content.toString();
	}

	/**
	 * 创建一个空的Sql对象
	 * @return
	 */
	public static Sql create() {
		return new Sql();
	}

	/**
	 * 创建 insert into 语句
	 * @param tname
	 * @param column
	 * @return insert into tname(column1, column2,...columnn) values(?, ?,...?)
	 */
	public static Sql createInsert(String tname, String... column) {
		return new Sql().insert(tname).lb().col(column).rb().values(column.length);
	}

	/**
	 * 创建 replace into 语句
	 * @param tname
	 * @param column
	 * @return replace into tname(column1, column2,...columnn) values(?, ?,...?)
	 */
	public static Sql createReplace(String tname, String... column) {
		return new Sql().replace(tname).lb().col(column).rb().values(column.length);
	}

	/**
	 * 创建 delete 语句
	 * @param tname
	 * @param where
	 * @return delete from tname where 1 = 1 and where1 = ? and where1 = ?...and wheren = ?
	 */
	public static Sql createDelete(String tname, String... where) {
		return new Sql().delete(tname).whereTrue().andColEq(where);
	}

	/**
	 * 创建 upate 语句
	 * @param tname
	 * @param columns
	 * @param where
	 * @return update tname set column1 = ?, column2 = ?...columnn = ? <br />
	 * where 1 = 1 and where1 = ? and where2 = ?... and wheren = ?
	 */
	public static Sql createUpdate(String tname, String[] columns, String... where) {
		return new Sql().update(tname).set().ucol(columns).whereTrue().andColEq(where);
	}

	/**
	 * 创建 select 语句
	 * @param tname
	 * @param columns
	 * @return select column1, column2...columnn from tname
	 */
	public static Sql createSelect(String tname, String... columns) {
		return new Sql().select().col(columns).from(tname);
	}
}
