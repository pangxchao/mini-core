package com.mini.jdbc.sql;

import static java.lang.String.format;

public interface SQL {

    String EXPANSION = " EXPANSION ";
    String ORDER_BY = " ORDER BY ";
    String GROUP_BY = " GROUP BY ";
    String DISTINCT = " DISTINCT ";
    String AGAINST = " AGAINST ";
    String BOOLEAN = " BOOLEAN ";
    String BETWEEN = " BETWEEN ";
    String REPLACE = " REPLACE ";
    String SELECT = " SELECT ";
    String INSERT = " INSERT ";
    String VALUES = " VALUES ";
    String DELETE = " DELETE ";
    String UPDATE = " UPDATE ";
    String HAVING = " HAVING ";
    String MATCH = " MATCH ";
    String WHERE = " WHERE ";
    String OUTER = " OUTER ";
    String QUERY = " QUERY ";
    String LIMIT = " LIMIT ";
    String UNION = " UNION ";
    String RIGHT = " RIGHT ";
    String NULL = " NULL ";
    String LIKE = " LIKE ";
    String MODE = " MODE ";
    String WITH = " WITH ";
    String INTO = " INTO ";
    String DESC = " DESC ";
    String FROM = " FROM ";
    String JOIN = " JOIN ";
    String LEFT = " LEFT ";
    String AND = " AND ";
    String OUT = " OUT ";
    String SET = " SET ";
    String NOT = " NOT ";
    String ASC = " ASC ";
    String AS = " AS ";
    String ON = " ON ";
    String OR = " OR ";
    String IN = " IN ";
    String IS = " IS ";
    String EMPTY = "";

    /** @return COUNT(*) */
    static String COUNT() {
        return " COUNT(*) ";
    }

    /** @return COUNT(name) */
    static String COUNT(String name) {
        return format(" COUNT(%s) ", name);
    }

    /** @return COUNT(name) as `alias` */
    static String COUNT(String name, String alias) {
        return format(" COUNT(%s) as `%s` ", name, alias);
    }

    /**
     * -生成内容列表
     * @return SQL
     */
    String toString();

    /**
     * -获取参数列表
     * @return 参数列表
     */
    Object[] toArray();

}
