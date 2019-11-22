layui.define(['jquery', 'form'], function (exports) {
    var $ = layui.$, form = layui.form;
    var load = function (obj, parent) {
        var sel = $(obj), child = sel.attr('child');
        var placeholder = sel.attr("placeholder");
        var text = sel.attr("text") || 'name';
        var val = sel.attr("val") || 'id';
        var selected = sel.attr("def");
        var url = sel.attr("url");
        var value = '', o;

        // 如果未配置url，可能这是第一个下拉， 数据从页面中来，不需要从服务器加载
        if (url === undefined || url === null || url === '') {
            load($("#" + child)[0], sel.val());
            return;
        }

        // 从后台获取当前选项的数据
        $.getJSON(url + parent, function (data) {
            sel.html(""); // 清空之前的数据
            // 创建空选项
            if (placeholder && placeholder !== '') {
                o = $('<option value=""></option>');
                if (!selected || selected === '') {
                    o.attr("selected", "true");
                    value = '';
                }
                sel.append(o);
            }
            // 创建选项
            if (data && layui.jquery.isArray(data)) {
                for (var i = 0; i < data.length; i++) {
                    o = $('<option value=""></option>');
                    if (selected === (data[i][val] + '')) {
                        o.attr("selected", "true");
                        value = data[i][val];
                    }
                    o.attr("value", data[i][val]);
                    o.text(data[i][text]);
                    sel.append(o);
                }
            }
            // 重新渲染Form表单
            form.render('select');
            // 加载子下拉列表
            if (!child || child === '') return;
            load($("#" + child)[0], value || '');
        });
    };

    exports('cascade', {
        render: function (obj, parent) {
            load($(obj)[0], parent);
        },
        change: function (data) {
            var child = $(data.elem).attr('child');
            if (!child || child === '') return;
            load($("#" + child), data.value);
        }
    });
});