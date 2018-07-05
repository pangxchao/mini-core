/**
 * Created the sn.mini.util.logger.Level.java
 * @created 2017年8月14日 下午5:00:39
 * @version 1.0.0
 */
package sn.mini.kotlin.util.logger

/**
 * sn.mini.util.logger.Level.java
 * @author XChao
 */
enum class Level constructor(val value: Int, val out: Int) {
    OFF(0, 0), //
    FATAL(1, 1),//
    ERROR(2, 3),//
    WARN(4, 7),//
    INFO(8, 15), //
    DEBUG(16, 31), //
    TRACE(32, 63),//
    ALL(64, 127) //
}
