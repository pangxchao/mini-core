layui.define(['ajax', 'laypage', 'jquery', 'layer'], function (exports) {
    var laypage = layui.laypage;
    var layer = layui.layer;
    var $ = layui.$;

    /**
     * 分页数据显示
     * @param renderTo 分页数据显示节点
     * @param pageOptions layui分页工具对象
     * @constructor
     */
    function MiniPage(renderTo, pageOptions) {
        this.totalEl = renderTo + "-total-value";
        this.pageEl = renderTo + "-page-value";
        this.pageOptions = pageOptions;
        this.renderTo = renderTo;
        this.index = undefined;
        this.dataOptions = {};

        var _this = this;
        this.pageOptions.jump = function (obj, first) {
            if (first === true) return;
            $.extend(_this.pageOptions, {
                limit: obj.limit,
                curr: obj.curr
            });
            _this.load(_this.dataOptions);
        };
    }

    MiniPage.prototype = {
        /**
         * 加载数据
         * @param options
         */
        load: function (options) {
            var _this = this;
            // 如果需要显示加载动画，则显示
            if (_this.pageOptions.loading === true) {
                _this.index = layer.load(2);
            }
            // // 保存 Ajax 请求参数
            $.extend(this.dataOptions, options, {
                success: function (data) {
                    $(_this.renderTo).html(data);
                    $.extend(_this.pageOptions, {
                        count: $(_this.totalEl).val(),
                        curr: $(_this.pageEl).val()
                    });

                    // 关闭加载层
                    if (_this.index !== undefined) {
                        layer.close(_this.index);
                        _this.index = undefined
                    }

                    // 显示完整的分页数据和按钮
                    laypage.render(_this.pageOptions);
                    if (typeof _this.pageOptions.onload === 'function') {
                        _this.pageOptions.onload.call(_this, //
                            _this.pageOptions);
                    }
                },
                dataType: "text"
            });

            // 添加分页需要的参数
            $.extend(this.dataOptions.data, {
                limit: _this.pageOptions.limit,
                page: _this.pageOptions.curr,
                dataType: 'text'
            });

            // 使用ajax模块并发送ajax请求
            layui.ajax(_this.dataOptions);
        },

        /**
         * 重新加载当前页
         */
        reload: function (data) {
            $.extend(this.dataOptions.data, data || {});
            this.load(this.dataOptions);
        }
    };

    exports('page', function (renderTo, options) {
        return new MiniPage(renderTo, $.extend({
            layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],
            curr: options.page === undefined ? 1 : options.page,
            elem: $(renderTo).attr("id") + "-page-button",
            limits: [10, 20, 30, 40, 50],
            loading: true,
            groups: 5,
            limit: 20
        }, options));
    });
});