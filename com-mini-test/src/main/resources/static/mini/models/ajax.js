layui.define(['jquery'], function (exports) {
    var $ = layui.$;

    /**
     * 给 FormData 添加数据
     * @param formData
     * @param key
     * @param value
     * @private
     */
    function _formDataAppendArray(formData, key, value) {
        if (!$.isArray(value)) {
            formData.append(key, value);
            return;
        }
        $.each(value, function (index, val) {
            formData.append(key, val);
        });
    }

    /**
     *  给FormData对象添加数据
     * @param formData
     * @param dataArray
     * @private
     */
    function _formDataAppendAll(formData, dataArray) {
        $.each(dataArray, function (key, val) {
            _formDataAppendArray(formData, key, val);
        });
    }

    /**
     * 初始化Ajax 参数
     * @param options
     * @private
     */
    function _initMiniAjaxParameter(options) {
        // 初始化请求类型
        if (!options.type) {
            options.type = "GET";
        }

        // 初始化返回参数
        if (!options.dataType) {
            options.dataType = "json";
        }
    }

    // 支持FormData对象的提交方式
    if ((typeof window.FormData) === 'function') {
        exports("ajax", function (options) {
            _initMiniAjaxParameter(options);
            if (options.form && options.form !== '') {
                var form = new FormData(options.form);
                _formDataAppendAll(form, options.data);
                options.processData = false;
                options.contentType = false;
                options.data = form;
            }
            $.ajax(options);
        });
        return;
    }

    // 不支持FormData对象的提交方式
    exports('ajax', function (options) {
        _initMiniAjaxParameter(options);
        $.ajax(options);
    });
});