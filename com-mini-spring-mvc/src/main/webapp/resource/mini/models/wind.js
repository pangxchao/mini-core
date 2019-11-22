layui.define(['jquery', 'ajax', 'layer', 'laytpl'], function (exports) {
    var extend = layui.$.extend;
    var open = layui.layer.open;
    var get = layui.$.get;
    var $ = layui.$;
    var wind = {};

    // 加载服务器请求的特殊弹出层(type=1)
    wind.request = function (url, options) {
        var index = layui.layer.load(2);
        var data = options.data || {};
        get(url, data, function (d) {
            layui.layer.close(index);
            open(extend(options, {
                content: d,
                type: 1
            }));
        }, 'text');
    };

    // 加载以模块为内容的特殊弹出层(type=1)
    wind.template = function (selector, options) {
        var html = $(selector).html();
        var d = options.data || {};
        var t = layui.laytpl(html);
        t.render(d, function (s) {
            open(extend(options, {
                content: s,
                type: 1
            }));
        });
    };

    // 返回对象
    exports('wind', wind);
});