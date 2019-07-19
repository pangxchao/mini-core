layui.define(['ajax', 'laypage', 'jquery'], function (exports) {
    var laypage = layui.laypage;
    var $ = layui.$;

    /**
     * 分页数据显示
     * @param renderTo 分页数据显示节点
     * @param options layui分页工具对象
     * @constructor
     */
    function MiniPage(renderTo, options) {
        this.renderTo = renderTo;
        this.options = options;
        this.aOptions = {};

        var _this = this;
        this.options.jump = function (obj, first) {
            if (first === true) return;
            _this.options.curr = obj.curr;
            _this.options.limit = obj.limit;
            _this.load(_this.aOptions);
        };
    }

    MiniPage.prototype = {
        /**
         * 加载数据
         * @param options
         */
        load: function (options) {
            var _this = this;

            // // 保存 Ajax 请求参数
            this.aOptions = options;

            // 添加分页需要的参数
            $.extend(this.aOptions.data, {
                page: _this.options.curr,
                rows: _this.options.limit
            });

            // ajax 回调
            _this.aOptions.dataType = "text";
            _this.aOptions.success = function (data) {
                $(_this.renderTo).html(data);
                _this.options.curr = $(_this.renderTo + "-page-value").val();
                _this.options.count = $(_this.renderTo + "-total-value").val();

                // 显示完整的分页数据和按钮
                laypage.render(_this.options);
                if(typeof _this.options.onload === 'function') {
                    _this.options.onload.call(_this);
                }
            };

            // 使用ajax模块并发送ajax请求
            layui.ajax(_this.aOptions);
        },

        /**
         * 重新加载当前页
         */
        reload: function () {
            this.load(this.aOptions);
        }
    };

    exports('template.page', function (renderTo, options) {
        // 分页按钮停靠位置
        if (!options.elem || options.elem === '') {
            options.elem = $(renderTo).attr("id") + "-page-button";
        }
        // 自定义排版
        if (!options.layout || !$.isArray(options.layout)) {
            options.layout = ['limit', 'count', 'prev', 'template.page', 'next', 'refresh', 'skip'];
        }
        return new MiniPage(renderTo, options);
    });
});