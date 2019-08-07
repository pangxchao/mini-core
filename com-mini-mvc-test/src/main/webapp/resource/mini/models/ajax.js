layui.define(['jquery'], function (exports) {
    /**
     * 给 FormData 添加数据
     * @param formData
     * @param key
     * @param value
     * @private
     */
    var _dataAppendArray = function (formData, key, value) {
        if (!layui.$.isArray(value)) {
            formData.append(key, value);
            return;
        }
        layui.$.each(value, function (index, val) {
            formData.append(key, val);
        });
    };

    /**
     *  给FormData对象添加数据
     * @param formData
     * @param dataArray
     * @private
     */
    var _dataAppendAll = function (formData, dataArray) {
        layui.$.each(dataArray, function (key, val) {
            _dataAppendArray(formData, key, val);
        });
    };

    /**
     * 支持FormData Ajax 提交
     * @param options
     * @constructor
     */
    var formDataAjaxFunction = function (options) {
        if (options.traditional === undefined) {
            options.traditional = true;
        }
        if (!options.form || options.form === '') {
            layui.$.ajax(options);
            return;
        }
        // processData与contentType必须为false
        var dom = layui.jquery(options.form);
        var f = new window.FormData(dom[0]);
        _dataAppendAll(f, options.data);
        options.contentType = false;
        options.processData = false;
        options.data = f;
        $.ajax(options);
    };

    // 支持FormData对象的提交方式
    if ((typeof window.FormData) === 'function') {
        exports("ajax", formDataAjaxFunction);
        return;
    }

    // 不支持FormData对象的提交方式
    exports('ajax', function (options) {
        if (options.traditional === undefined) {
            options.traditional = true;
        }
        layui.$.ajax(options);
    });
});