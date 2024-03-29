package com.mini.core.jdbc.database;

import com.mini.core.jdbc.MiniJdbcTemplate;

import javax.annotation.Nonnull;

/**
 * 数据库版本升级
 *
 * @author pangchao
 */
public interface UpgradeTable {

    /**
     * 因为数据库有些隐式事务的提交，需要将结构更新与数据更新分开
     * <ul>
     *     <li>升级时每个版本先调用结构升级，再调用数据升级</li>
     *     <li>结构升级时没有事务控制，所以需要保证重复调用不出错误</li>
     *     <li>升级数据时，每个版本都会单独开启一个事务控制</li>
     * </ul>
     *
     * @param upgradeCallback 升级回调
     * @see MiniJdbcTemplate#hasTable(String)
     * @see MiniJdbcTemplate#hasColumn(String, String)
     */
    void upgrade(@Nonnull UpgradeCallback upgradeCallback);
}
