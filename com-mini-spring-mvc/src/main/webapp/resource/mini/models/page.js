layui.define(['jquery', 'ajax', 'laypage', 'layer'], function (exports) {
    var layout = ['prev', 'page', 'next', 'skip', 'count', 'limit'];
    var laypage = layui.laypage;
    var layer = layui.layer;
    var $ = layui.$;

    /**
     * 分页数据显示
     * @param ele 分页数据显示节点
     * @param options layui分页工具对象
     * @constructor
     */
    function MiniPage(ele, options) {
        this.totalEl = ele + "-total-value";
        this.pageEl = ele + "-page-value";
        this.options = options;
        this.ele = ele;
    }

    MiniPage.prototype.load = function (url, data) {
        var t = this;
        if (!!this.options.loading) {
            t.index = layer.load(2);
        }

        this.url = url;
        this.data = $.extend({data: {}}, data, {
            success: function (resp) {
                $(t.ele).html(resp);

                // 获取分页数据
                var tl = $(t.totalEl);
                var pl = $(t.pageEl);

                // 重新设置分页数据
                $.extend(t.options, {
                    count: tl.val(),
                    curr: pl.val()
                });

                // 关闭加载层
                if (t.index !== undefined) {
                    layer.close(t.index);
                    t.index = undefined
                }

                // 显示完整的分页数据和按钮
                laypage.render(t.options);
                if (!!t.options.complete) {
                    t.options.complete(t);
                }
            },
            dataType: "text"
        });
        // 加载数据
        this.reload({});
        return this;
    };

    MiniPage.prototype.reload = function (data) {
        $.extend(this.data.data, data, {
            limit: this.options.limit,
            page: this.options.curr
        });
        // 加载数据
        layui.ajax(this.url, this.data);
        return this;
    };

    exports('page', function (renderTo, options) {
        var page = new MiniPage(renderTo, $.extend({
            elem: $(renderTo).id + "-page-button",
            limits: [10, 20, 30, 40, 50],
            layout: layout,
            loading: true,
            groups: 5,
            limit: 10,
            curr: 1
        }, options, {
            jump: function (obj, first) {
                if (first === true) {
                    return;
                }

                // 重新设置分页数据
                $.extend(page.options, {
                    limit: obj.limit,
                    curr: obj.curr
                });

                // 重装加载数据
                page.reload(page.data);
            }
        }));
        return page;
    });
});