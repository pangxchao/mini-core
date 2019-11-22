<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <base href="${request.contextPath}/"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>后台管理实体信息-首页</title>
    <link type="text/css" rel="stylesheet" href="resource/layui/css/layui.css"/>
    <link type="text/css" rel="stylesheet" href="resource/mini/css/mini.css">
    <script type="text/javascript" src="resource/layui/layui.js"></script>
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
                <label class="layui-form-label mini-form-label">用户名/昵称</label>
                <label class="layui-input-inline">
                    <input type="text" name="search" class="layui-input"/>
                </label>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label mini-form-label">创建时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="startTime" class="layui-input" id="startTime" readonly/>
                </div>
                <label class="layui-form-mid">-</label>
                <div class="layui-input-inline ">
                    <input type="text" name="endTime" class="layui-input" id="endTime" readonly/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label mini-form-label">所属地区</label>
            <div class="layui-input-inline">
                <select name="province" id="province" url="mobile/region/list.htm?id=" child="city" placeholder="请选择省">
                    <option value="">请选择省</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <select name="city" id="city" url="mobile/region/list.htm?id=" child="district" placeholder="请选择市">
                    <option value="">请选择市</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <select name="district" id="district" url="mobile/region/list.htm?id=" placeholder="请选择县/区">
                    <option value="">请选择县/区</option>
                </select>
            </div>
            <div class="layui-form-mid layui-word-aux">此处演示联动排版和功能</div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label mini-form-label">用户类型</label>
            <div class="layui-input-block">
                <input type="checkbox" name="phone" value="1" title="手机已认证" lay-filter="phone"/>
                <input type="checkbox" name="email" value="1" title="邮箱已认证" lay-filter="email"/>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block mini-input-block">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="searchForm">搜索</button>
                <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">清空/重置</button>
                <button type="button" class="layui-btn layui-btn-sm layui-btn-primary" id="myBtn">自定义</button>
            </div>
        </div>
    </form>
    <#include "pages.ftl">
</div>

<script type="text/html" id="insertTemplate">
    <#include "insert.ftl">
</script>

<script type="text/html" id="updateTemplate">
    <#include "update.ftl">
</script>


<script type="text/javascript">
    layui.use(['table', 'form', 'wind', 'laydate', 'cascade', 'upload'], function () {
        layui.table.render();
        layui.form.render();

        // 注册日期选择控件
        layui.laydate.render({elem: '#startTime'});
        layui.laydate.render({elem: '#endTime'});

        // 级联下拉实现
        layui.cascade.render("#province", 0);
        layui.form.on('select', function (data) {
            layui.cascade.change(data);
        });

        // 自定义按钮点击事件处理
        $("#myBtn").on("click", function () {
            layer.msg("自定义按钮点击事件");
            return false;
        });

        // 添加方法
        var isUpload, headUrl, uploadUI;
        var insertFunction = function () {
            layui.wind.template('#insertTemplate', {
                area: ['800px', '800px'],
                skin: 'layui-layer-rim',
                title: '添加实体信息',
                id: "insertPage",
                btn: ['确定'],
                yes: function () {
                    if (isUpload === true) {
                        uploadUI.upload();
                    } else {
                        $("#insertButton").click();
                    }
                },
                success: function (lay, index) {
                    layui.form.render();

                    // 级联下拉实现
                    layui.cascade.render("#provinceEdit", 0);
                    layui.form.on('select', function (data) {
                        layui.cascade.change(data);
                    });

                    // 选完文件后不自动上传
                    uploadUI = layui.upload.render({
                        url: 'mobile/demo/upload.htm',
                        elem: '#headUrlFile',
                        accept: 'file',
                        field: 'file',
                        auto: false,
                        data: {mark: "mark"},
                        choose: function () {
                            isUpload = true;
                        },
                        // 上传完成回调
                        done: function (data) {
                            headUrl = data.url;
                            $("#insertButton").click();
                            isUpload = false;
                        }
                    });

                    // 绑定 Insert Form 提交事件
                    layui.form.on("submit(insertButton)", function (data) {
                        layui.ajax('back/demo/insert.htm', {
                            data: $.extend({}, data.field, {
                                headUrl: headUrl
                            }),
                            success: function (resp, state, xhr) {
                                layer.msg("添加用户成功");
                                layer.close(index);
                                // 重新加载表格数据
                                layui.table.reload('test');
                                console.log(xhr)
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
            <#--noinspection JSUnresolvedVariable-->
            var uris = info.regionIdUri.split(".");
            var data = $.extend({}, info, {
                regionId: uris[2],
                province: uris[0],
                city: uris[1]
            });
            layui.wind.template('#updateTemplate', {
                area: ['800px', '800px'],
                skin: 'layui-layer-rim',
                title: '修改实体信息',
                id: "insertPage",
                btn: ['确定'],
                data: data,
                yes: function () {
                    if (isUpload === true) {
                        uploadUI.upload();
                    } else {
                        $("#updateButton").click();
                    }
                },
                success: function (lay, index) {
                    layui.form.render();
                    layui.form.val("updateForm", info);

                    // 级联下拉实现
                    layui.cascade.render("#provinceEdit", 0);
                    layui.form.on('select', function (data) {
                        layui.cascade.change(data);
                    });

                    // 选完文件后不自动上传
                    uploadUI = layui.upload.render({
                        url: 'mobile/demo/upload.htm',
                        elem: '#headUrlFile',
                        accept: 'file',
                        field: 'file',
                        auto: false,
                        data: {mark: "mark"},
                        choose: function () {
                            isUpload = true;
                        },
                        // 上传完成回调
                        done: function (data) {
                            headUrl = data.url;
                            $("#updateButton").click();
                        }
                    });

                    // 绑定 Insert Form 提交事件
                    layui.form.on("submit(updateButton)", function (data) {
                        layui.ajax('back/demo/update.htm', {
                            data: $.extend(info, data.field, {
                                headUrl: headUrl,
                                id: info.id
                            }),
                            success: function (resp, state, xhr) {
                                layer.msg("修改用户成功");
                                layer.close(index);
                                // 重新加载表格数据
                                layui.table.reload('test');
                                console.log(xhr)
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
                layui.ajax('back/demo/delete.htm', {
                    data: {idList: idList},
                    success: function () {
                        layer.msg("删除用户成功");
                        layer.close(index);
                        // 重新加载表格数据
                        layui.table.reload('test');
                    },
                    error: function (response, state) {
                        layer.msg(response.responseText);
                        console.log(state);
                    },
                    type: "POST"
                });
            });
        };

        // form 提交事件(处理数据的搜索)
        var initValue = {
            city: "",
            district: "",
            endTime: "",
            province: "",
            search: "",
            startTime: "",
            phone: '',
            email: ''
        };
        layui.form.on("submit(searchForm)", function (data) {
            var d = $.extend({}, initValue, data.field);
            console.log(d);
            layui.table.reload('test', {
                traditional: true,
                where: d
            });
            return false;
        });

        // 监听数据表格排序事件
        layui.table.on('sort(test)', function (obj) {
            layui.table.reload('test', {
                initSort: obj,
                where: {
                    //排序字段
                    sortField: obj.field,
                    //排序方式
                    sortType: obj.type === 'asc' ? 0 : 1
                }
            });
        });

        // 监听头部工具栏点击事件
        layui.table.on('toolbar(test)', function (obj) {
            // 获取选中的行
            var checkStatus = layui.table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            console.log(data);
            // 添加按钮
            if (obj.event === 'add') {
                insertFunction();
            }
            // 删除按钮
            else if (obj.event === 'delete') {
                var idList = [], i;
                for (i = 0; i < data.length; i++) {
                    idList[i] = data[i].id;
                }
                deleteFunction(idList);
            }
        });

        // 监听每行的工具条事件
        layui.table.on("tool(test)", function (obj) {
            //  查看按钮点击事件
            if (obj.event === 'detail') {
                // layer.msg('查看');
                // window.open('https://www.layui.com/demo/form.html', '_blank');
            }
            // 编辑按钮事件
            else if (obj.event === 'edit') {
                updateFunction(obj.data);
            }
            // 删除按钮点击事件
            else if (obj.event === 'del') {
                var idList = [obj.data.id];
                deleteFunction(idList);
            }
            // 认证手机号按钮
            else if (obj.event === 'auth') {
                layer.alert("您点击了‘认证手机’按钮")
            }
        });
    });
</script>
</body>
</html>