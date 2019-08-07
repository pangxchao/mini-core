package com.mini.logger;

public interface Logger {

    /**
     * 获取输出日志级别
     * @return 日志级别
     */
    Level getLevel();

    /**
     * 是否可以输出错误日志
     * @return true - 是
     */
    boolean isVerboseEnabled();

    /**
     * 是否可以输出错误日志
     * @return true - 是
     */
    boolean isDebugEnabled();

    /**
     * 是否可以输出错误日志
     * @return true - 是
     */
    boolean isInfoEnabled();

    /**
     * 是否可以输出错误日志
     * @return true - 是
     */
    boolean isWarnEnabled();

    /**
     * 是否可以输出错误日志
     * @return true - 是
     */
    boolean isErrorEnabled();

    /**
     * 输出详细信息日志
     * @param message 日志内容
     */
    void verbose(Object message);

    /**
     * 输出详细信息日志
     * @param throwable 异常信息
     */
    void verbose(Throwable throwable);

    /**
     * 输出详细信息日志
     * @param message   日志内容
     * @param throwable 异常信息
     */
    void verbose(Object message, Throwable throwable);

    /**
     * 输出调试日志
     * @param message 日志内容
     */
    void debug(Object message);

    /**
     * 输出调试日志
     * @param throwable 异常信息
     */
    void debug(Throwable throwable);

    /**
     * 输出调试日志
     * @param message   日志内容
     * @param throwable 异常信息
     */
    void debug(Object message, Throwable throwable);

    /**
     * 输出信息日志
     * @param message 日志内容
     */
    void info(Object message);

    /**
     * 输出信息日志
     * @param throwable 异常信息
     */
    void info(Throwable throwable);

    /**
     * 输出信息日志
     * @param message   日志内容
     * @param throwable 异常信息
     */
    void info(Object message, Throwable throwable);

    /**
     * 输出警告日志
     * @param message 日志内容
     */
    void warn(Object message);

    /**
     * 输出警告日志
     * @param throwable 异常信息
     */
    void warn(Throwable throwable);

    /**
     * 输出警告日志
     * @param message   日志内容
     * @param throwable 异常信息
     */
    void warn(Object message, Throwable throwable);

    /**
     * 输出错误日志
     * @param message 日志内容
     */
    void error(Object message);

    /**
     * 输出错误日志
     * @param throwable 异常信息
     */
    void error(Throwable throwable);

    /**
     * 输出错误日志
     * @param message   日志内容
     * @param throwable 异常信息
     */
    void error(Object message, Throwable throwable);
}
