package sn.mini.kotlin.util.logger

object LogUtil {
    private var instance: ILogger = SNLogger()

    /**
     * 设置输出日志实现类
     *
     * @param logger
     */
    fun setInstance(logger: Class<out ILogger>) {
        LogUtil.instance = logger.getConstructor().newInstance()
    }

    /**
     * 输出日志
     *
     * @param message
     */
    fun fatal(message: String) {
        if (instance.isFatalEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doFatal(getMessageByElement(element, message))
        }
    }

    /**
     * 输出日志
     *
     * @param message
     * @param throwable
     */
    fun fatal(message: String, throwable: Throwable) {
        if (instance.isFatalEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doFatal(getMessageByElement(element, message), throwable)
        }
    }

    /**
     * 输出日志
     *
     * @param message
     */
    fun error(message: String) {
        if (instance.isErrorEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doError(getMessageByElement(element, message))
        }
    }

    /**
     * 输出日志
     *
     * @param message
     * @param throwable
     */
    fun error(message: String, throwable: Throwable) {
        if (instance.isErrorEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doError(getMessageByElement(element, message), throwable)
        }
    }

    /**
     * 输出日志
     *
     * @param message
     */
    fun warn(message: String) {
        if (instance.isWarnEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doWarn(getMessageByElement(element, message))
        }
    }

    /**
     * 输出日志
     *
     * @param message
     * @param throwable
     */
    fun warn(message: String, throwable: Throwable) {
        if (instance.isWarnEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doWarn(getMessageByElement(element, message), throwable)
        }
    }

    /**
     * 输出日志
     *
     * @param message
     */
    fun info(message: String) {
        if (instance.isInfoEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doInfo(getMessageByElement(element, message))
        }
    }

    /**
     * 输出日志
     *
     * @param message
     * @param throwable
     */
    fun info(message: String, throwable: Throwable) {
        if (instance.isInfoEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doInfo(getMessageByElement(element, message), throwable)
        }
    }

    /**
     * 输出日志
     *
     * @param message
     */
    fun debug(message: String) {
        if (instance.isDebugEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doDebug(getMessageByElement(element, message))
        }
    }

    /**
     * 输出日志
     *
     * @param message
     * @param throwable
     */
    fun debug(message: String, throwable: Throwable) {
        if (instance.isDebugEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doDebug(getMessageByElement(element, message), throwable)
        }
    }

    /**
     * 输出日志
     *
     * @param message
     */
    fun trace(message: String) {
        if (instance.isTraceEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doTrace(getMessageByElement(element, message))
        }
    }

    /**
     * 输出日志
     *
     * @param message
     * @param throwable
     */
    fun trace(message: String, throwable: Throwable) {
        if (instance.isTraceEnabled()) {
            val element = getStackTraceElement(Throwable())
            instance.doTrace(getMessageByElement(element, message), throwable)
        }
    }

    private fun getStackTraceElement(throwable: Throwable): StackTraceElement {
        val elements = throwable.stackTrace
        return if (elements.size > 1) elements[1] else elements[0]
    }

    private fun getMessageByElement(element: StackTraceElement, message: String): String {
        val build = StringBuilder().append(element.className).append(".")
        build.append(element.methodName).append("[Line:").append(element.lineNumber)
        return build.append("] - ").append(message).toString()
    }
}
