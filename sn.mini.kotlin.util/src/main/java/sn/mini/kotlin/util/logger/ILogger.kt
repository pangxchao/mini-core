package sn.mini.kotlin.util.logger

interface ILogger {
    fun isFatalEnabled(): Boolean

    fun isErrorEnabled(): Boolean

    fun isWarnEnabled(): Boolean

    fun isInfoEnabled(): Boolean

    fun isDebugEnabled(): Boolean

    fun isTraceEnabled(): Boolean

    fun doFatal(message: String)

    fun doFatal(message: String, throwable: Throwable)

    fun doError(message: String)

    fun doError(message: String, throwable: Throwable)

    fun doWarn(message: String)

    fun doWarn(message: String, throwable: Throwable)

    fun doInfo(message: String)

    fun doInfo(message: String, throwable: Throwable)

    fun doDebug(message: String)

    fun doDebug(message: String, throwable: Throwable)

    fun doTrace(message: String)

    fun doTrace(message: String, throwable: Throwable)
}