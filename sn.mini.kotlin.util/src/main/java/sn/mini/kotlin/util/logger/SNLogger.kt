package sn.mini.kotlin.util.logger

import java.text.SimpleDateFormat
import java.util.*

class SNLogger : ILogger {

    override fun isFatalEnabled(): Boolean {
        return (level.out and Level.FATAL.value) == Level.FATAL.value
    }

    override fun isErrorEnabled(): Boolean {
        return (level.out and Level.ERROR.value) == Level.ERROR.value
    }

    override fun isWarnEnabled(): Boolean {
        return (level.out and Level.WARN.value) == Level.WARN.value
    }

    override fun isInfoEnabled(): Boolean {
        return (level.out and Level.INFO.value) == Level.INFO.value
    }

    override fun isDebugEnabled(): Boolean {
        return (level.out and Level.DEBUG.value) == Level.DEBUG.value
    }

    override fun isTraceEnabled(): Boolean {
        return (level.out and Level.TRACE.value) == Level.TRACE.value
    }

    override fun doFatal(message: String) {
        this.log(Level.FATAL, message)

    }

    override fun doFatal(message: String, throwable: Throwable) {
        this.log(Level.FATAL, message, throwable)

    }

    override fun doError(message: String) {
        this.log(Level.ERROR, message)
    }

    override fun doError(message: String, throwable: Throwable) {
        this.log(Level.ERROR, message, throwable)
    }

    override fun doWarn(message: String) {
        this.log(Level.WARN, message)
    }

    override fun doWarn(message: String, throwable: Throwable) {
        this.log(Level.WARN, message, throwable)
    }

    override fun doInfo(message: String) {
        this.log(Level.INFO, message)

    }

    override fun doInfo(message: String, throwable: Throwable) {
        this.log(Level.INFO, message, throwable)
    }

    override fun doDebug(message: String) {
        this.log(Level.DEBUG, message)
    }

    override fun doDebug(message: String, throwable: Throwable) {
        this.log(Level.DEBUG, message, throwable)
    }

    override fun doTrace(message: String) {
        this.log(Level.TRACE, message)
    }

    override fun doTrace(message: String, throwable: Throwable) {
        this.log(Level.TRACE, message, throwable)
    }

    private fun log(lev: Level, message: String) {
        if (level.out and lev.value == lev.value) {
            println(String.format("%s - %s [%s] \r\n%s", format.format(Date()), this.javaClass.name, level.name, message))
        }
    }

    private fun log(lev: Level, message: String, throwable: Throwable) {
        if (level.out and lev.value == lev.value) {
            println(String.format("%s - %s [%s] \r\n%s", format.format(Date()), this.javaClass.name, level.name, message))
            throwable.printStackTrace(System.out)
        }
    }

    companion object {
        private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        // 输出日志级别
        private var level: Level = Level.DEBUG

        /**
         * 设置输入日志级别
         * @param level
         */
        fun setLevel(level: Level) {
            SNLogger.level = level
        }
    }
}
