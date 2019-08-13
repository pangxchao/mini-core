layui.define(['jquery'], function (exports) {
    var $ = layui.$;
    var append_array = function (formData, key, value) {
        if (!layui.jquery.isArray(value)) {
            formData.append(key, value);
            return;
        }
        $.each(value, function (index, val) {
            formData.append(key, val);
        });
    };


    var append_all = function (formData, dataArray) {
        $.each(dataArray, function (key, val) {
            append_array(formData, key, val);
        });
        return formData;
    };

    var result = !!FormData ? function (url, options) {
        if (!options.form || options.form === '') {
            layui.$.ajax($.extend({
                traditional: true
            }, options, {
                url: url
            }));
            return;
        }

        var formData = new FormData($(options.form)[0]);
        $.ajax($.extend({traditional: true}, options, {
            data: append_all(formData, options.data),
            contentType: false,
            processData: false,
            url: url
        }));

    } : function (url, options) {
        layui.$.ajax($.extend({
            traditional: true
        }, options, {
            url: url
        }));
    };

    result.get = function (url, data, options) {
        result(url, $.extend({}, options, {
            type: "GET",
            data: data
        }));
    };

    result.post = function (url, data, options) {
        result(url, $.extend({}, options, {
            type: "POST",
            data: data
        }));
    };

    // 返回对象
    exports('ajax', result);
});