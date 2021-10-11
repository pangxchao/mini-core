package com.mini.core.jdbc.database;

import com.mini.core.jdbc.MiniJdbcTemplate;

import java.util.function.Consumer;

/**
 * 升级回调
 *
 * @author pangchao
 */
public interface UpgradeCallback {
    @SuppressWarnings("UnusedReturnValue")
    default boolean upgradeStructureToVersion(int version, Consumer<MiniJdbcTemplate> consumer) {
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
    default boolean upgradeDataToVersion(int version, Consumer<MiniJdbcTemplate> consumer) {
        return false;
    }
}
