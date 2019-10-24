<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    ${r'<#--noinspection FtlReferencesInspection-->'}
    <base href="${r'${request.contextPath}'}/"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>后台管理实体信息-首页</title>
    ${r'<#--noinspection HtmlUnknownTarget-->'}
    <#--noinspection HtmlUnknownTarget-->
    <link type="text/css" rel="stylesheet" href="resource/layui/css/layui.css"/>
    ${r'<#--noinspection HtmlUnknownTarget-->'}
    <#--noinspection HtmlUnknownTarget-->
    <link type="text/css" rel="stylesheet" href="resource/mini/css/mini.css">
    ${r'<#--noinspection HtmlUnknownTarget-->'}
    <#--noinspection HtmlUnknownTarget-->
    <script type="text/javascript" src="resource/layui/layui.js"></script>
    ${r'<#--noinspection HtmlUnknownTarget-->'}
    <#--noinspection HtmlUnknownTarget-->
    <script type="text/javascript" src="resource/mini/mini.js"></script>
    <style type="text/css">
        .layui-form-label {
            width: 95px;
        }

        .mini-input-block {
            margin-left: 110px;
        }
    </style>
</head>
<body>
<div class="layui-main mini-main">
    <blockquote class="layui-elem-quote">用户信息管理</blockquote>
    <form class="layui-form mini-form" action="">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label mini-form-label">搜索</label>
                <label class="layui-input-inline">
                    <input type="text" name="search" class="layui-input"/>
                </label>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block mini-input-block">
                ${r'<#--noinspection HtmlUnknownAttribute-->'}
                <#--noinspection HtmlUnknownAttribute-->
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="searchForm">搜索</button>
                <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">清空/重置</button>
            </div>
        </div>
    </form>
    ${r'<#include "pages.ftl">'}
</div>

<script type="text/html" id="insertTemplate">
    ${r'<#include "insert.ftl">'}
</script>

<script type="text/html" id="updateTemplate">
    ${r'<#include "update.ftl">'}
</script>

<script type="text/javascript">
    layui.use(['table', 'form', 'page', 'wind'], function () {
        layui.table.render();
        layui.form.render();

        // form 提交事件(处理数据的搜索)
        layui.form.on("submit(searchForm)", function (data) {
            var d = $.extend({}, {search: ""}, data.field);
            <#--noinspection FtlReferencesInspection-->
            layui.table.reload('${name}-table', {
                traditional: true,
                where: d
            });
            return false;
        });

        // 添加方法
        var insertFunction = function () {
            layui.wind.template('#insertTemplate', {
                area: ['800px', '800px'],
                skin: 'layui-layer-rim',
                title: '添加实体信息',
                id: "insertPage",
                btn: ['确定'],
                yes: function () {
                    $("#insertButton").click();
                },
                success: function (lay, index) {
                    layui.form.render();

                    // 绑定 Insert Form 提交事件
                    layui.form.on("submit(insertButton)", function (data) {
                        <#--noinspection FtlReferencesInspection-->
                        layui.ajax('back/${name}/insert.htm', {
                            data: $.extend({}, data.field),
                            success: function (resp) {
                                layer.msg("添加信息成功");
                                layer.close(index);
                                console.log(resp);
                                // 重新加载表格数据
                                <#--noinspection FtlReferencesInspection-->
                                layui.table.reload('${name}-table');
                            },
                            error: function (response, state) {
                                layer.msg(response.responseText);
                                console.log(state);
                            },
                            type: "POST"
                        });
                        return false;
                    });
                }
            });
        };

        // 修改方法
        var updateFunction = function (info) {
            var data = $.extend({}, info, {});
            layui.wind.template('#updateTemplate', {
                area: ['800px', '800px'],
                skin: 'layui-layer-rim',
                title: '修改实体信息',
                id: "insertPage",
                btn: ['确定'],
                data: data,
                yes: function () {
                    $("#updateButton").click();
                },
                success: function (lay, index) {
                    layui.form.render();
                    layui.form.val("updateForm", info);

                    // 绑定 Insert Form 提交事件
                    layui.form.on("submit(updateButton)", function (data) {
                        <#--noinspection FtlReferencesInspection-->
                        layui.ajax('back/${name}/update.htm', {
                            data: $.extend(info, data.field, {
                                id: info.id
                            }),
                            success: function (resp) {
                                layer.msg("修改信息成功");
                                layer.close(index);
                                console.log(resp);
                                // 重新加载表格数据
                                <#--noinspection FtlReferencesInspection-->
                                layui.table.reload('${name}-table');
                            },
                            error: function (response, state) {
                                layer.msg(response.responseText);
                                console.log(state);
                            },
                            type: "POST"
                        });
                        return false;
                    });
                }
            });
        };

        var deleteFunction = function (idList) {
            if (!idList || idList.length <= 0) {
                layer.alert("未选中数据");
                return;
            }
            layer.confirm("是否删除选中数据", function (index) {
                <#--noinspection FtlReferencesInspection-->
                layui.ajax('back/${name}/delete.htm', {
                    data: {idList: idList},
                    success: function () {
                        layer.msg("删除用户成功");
                        layer.close(index);
                        // 重新加载表格数据
                        <#--noinspection FtlReferencesInspection-->
                        layui.table.reload('${name}-table');
                    },
                    error: function (response, state) {
                        layer.msg(response.responseText);
                        console.log(state);
                    },
                    type: "POST"
                });
            });
        };

        // 监听头部工具栏点击事件
        <#--noinspection FtlReferencesInspection-->
        layui.table.on('toolbar(${name}-table)', function (obj) {
            // 添加按钮
            if (obj.event === 'add') {
                insertFunction();
            }
            // 删除按钮
            else if (obj.event === 'delete') {
                var idList = [], i, id = obj.config.id;
                var data = layui.table.checkStatus(id);
                for (i = 0; i < data.data.length; i++) {
                    idList[i] = data.data[i].id;
                }
                deleteFunction(idList);
            }
        });

        // 监听每行的工具条事件
        <#--noinspection FtlReferencesInspection-->
        layui.table.on("tool(${name}-table)", function (obj) {
            // 编辑按钮事件
            if (obj.event === 'edit') {
                updateFunction(obj.data);
            }
            // 删除按钮点击事件
            else if (obj.event === 'del') {
                var idList = [obj.data.id];
                deleteFunction(idList);
            }
        });
    });
</script>
</body>
</html>