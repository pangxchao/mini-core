package com.mini.jdbc.sql;

public interface SQL {
    String REPLACE = " REPLACE ";
    String INSERT = " INSERT ";
    String INTO = " INTO ";
    String VALUES = " VALUES ";
    String DELETE = " DELETE ";
    String FROM = " FROM ";
    String UPDATE = " UPDATE ";
    String SET = " SET ";
    String SELECT = " SELECT ";
    String DISTINCT = " DISTINCT ";
    String AS = " AS ";
    String JOIN = " JOIN ";
    String LEFT = " LEFT ";
    String RIGHT = " RIGHT ";
    String OUT = " OUT ";
    String OUTER = " OUTER ";
    String ON = " ON ";
    String WHERE = " WHERE ";
    String AND = " AND ";
    String OR = " OR ";
    String IN = " IN ";
    String NOT = " NOT ";
    String IS = " IS ";
    String NULL = " NULL ";
    String LIKE = " LIKE ";
    String MATCH = " MATCH ";
    String AGAINST = " AGAINST ";
    String BOOLEAN = " BOOLEAN ";
    String MODE = " MODE ";
    String WITH = " WITH ";
    String QUERY = " QUERY ";
    String EXPANSION = " EXPANSION ";
    String BETWEEN = " BETWEEN ";
    String ORDER_BY = " ORDER BY ";
    String GROUP_BY = " GROUP BY ";
    String ASC = " ASC ";
    String DESC = " DESC ";
    String LIMIT = " LIMIT ";
    String UNION = " UNION ";
    String HAVING = " HAVING ";

    /**
     * -生成内容列表
     * @return SQL
     */
    String content();

    /**
     * -获取参数列表
     * @return 参数列表
     */
    Object[] params();

}
