layui.config({base: 'resource/mini/models/'}).extend({
    cascade: 'cascade', // 表单拉下级联扩展
    ajax: 'ajax', // 扩展jQuery Ajax 方法扩展
    page: 'page'  // 扩展LayUI laypage 模块扩展
}).use(['jquery', 'ajax'], function () {
    window.$ = layui.$;
});

/**
 * 对日期格式化
 * @param format 格式化字符串
 * <ul>
 *     <li>yyyy: 4位年。如：2018年(2018)</li>
 *     <li>yy: 两位年。如：2018年(18)</li>
 *     <li>M: 月，小于10时无需补0。如：5月(5), 11月(11)</li>
 *     <li>MM: 月，小于10时前面需要补0。如：5月(05), 11月(11)</li>
 *     <li>d: 日期，小于10时无需补0，如：3日(3), 20日(20)</li>
 *     <li>dd: 日期，小于10时前面需要补0，如：3日(03), 20日(20)</li>
 *     <li>h: 12小时制，小于10时无需补0，如：3时(3), 13时(1)</li>
 *     <li>hh: 12小时制，小于10时前面需要补0，如：3时(03), 13时(01)</li>
 *     <li>H: 24小时制，小于10时无需补0，如：3时(3), 13时(13)</li>
 *     <li>HH: 24小时制，小于10时前面需要补0，如：3时(03), 13时(13)</li>
 *     <li>m: 分钟，小于10时无需补0，如：3分(3), 20分(20)</li>
 *     <li>mm: 分钟，小于10时前面需要补0，如：3分(03), 20分(20)</li>
 *     <li>s: 秒，小于10时无需补0，如：3秒(3), 20秒(20)</li>
 *     <li>ss: 秒，小于10时前面需要补0，如：3秒(03), 20秒(20)</li>
 *     <li>S: 3位毫秒，如：3毫秒(003), 40毫秒(040), 200毫秒(200)</li>
 *     <li>E: 两位中文星期几，如：星期二(周二)</li>
 *     <li>E: 三位中文星期几，如：星期二(星期二)</li>
 *     <li>Z: 时区</li>
 * </ul>
 * @return 格式化结果
 */
Date.prototype.format = function (format) {
    var nFormat = function (value, length) {
        var val = String(value);
        var i = val.length;
        for (; i < length; i++) {
            val = '0' + val;
        }
        return val;
    };


    var e = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
    var se = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
    var _this = this, result = String(format);

    // 处理年
    result = result.replace(/(y+)/g, function (year) {
        var v = _this.getFullYear(), l = year.length;
        return year.length > 2 ? nFormat(v, l) //
            : nFormat(v, l).substr(2)
    });

    // 处理月份
    result = result.replace(/(M+)/g, function (month) {
        return month.length === 1 ? _this.getMonth() + 1//
            : nFormat(_this.getMonth() + 1, 2);
    });

    // 处理日期
    result = result.replace(/(d+)/g, function (date) {
        return date.length === 1 ? _this.getDate() //
            : nFormat(_this.getDate(), 2);
    });

    // 24小时制小时
    result = result.replace(/(H+)/g, function (hours) {
        return hours.length === 1 ? _this.getHours() //
            : nFormat(_this.getHours(), 2);
    });

    // 12小时制小时
    result = result.replace(/(h+)/g, function (hours) {
        var val = _this.getHours() % 12 || 12;//
        return hours.length === 1 ? val  //
            : nFormat(val, 2);
    });

    // 处理分钟
    result = result.replace(/(m+)/g, function (minutes) {
        return minutes.length === 1 ? _this.getMinutes() //
            : nFormat(_this.getMinutes(), 2);
    });

    // 处理秒
    result = result.replace(/(s+)/g, function (seconds) {
        return seconds.length === 1 ? _this.getSeconds() //
            : nFormat(_this.getSeconds(), 2);
    });

    // 处理毫秒
    result = result.replace(/(S+)/g, function () {
        return nFormat(_this.getMilliseconds(), 3);
    });

    // 处理英文星期
    result = result.replace(/(E+)/g, function (week) {
        return week.length === 1 ? se[_this.getDay()] //
            : e[_this.getDay()];
    });
    return result;
};