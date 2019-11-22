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

    <div id="content">

    </div>
    <div id="content-page-button">

    </div>
</div>

<!-- 头部工具拦演示 -->
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" onclick="window.insertFunction()">添加</button>
        <button class="layui-btn layui-btn-sm" onclick="window.deleteAllFunction()">删除</button>
    </div>
</script>

<script type="text/javascript">
    layui.use(['table', 'form', 'page', 'wind', 'laydate', 'cascade', 'upload'], function () {
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
            //layer.msg("自定义按钮点击事件");
            insertFunction();
            return false;
        });


        // 该options为layui分页相关数据
        var page = layui.page("#content", {
            elem: "content-page-button",
            complete: function () {
                // 转化静态表格
                layui.table.init('test', {
                    id: 'test'
                });
            }
        });
        // 该 options 为Ajax相关数据
        page.load('front/demo/pages.htm', {});

        // 添加方法
        var isUpload, headUrl, uploadUI;
        window.insertFunction = function () {
            layui.wind.request("front/demo/insert.htm", {
                area: ['800px', '800px'],
                skin: 'layui-layer-rim',
                title: '修改实体信息',
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
                        choose: function (obj) {
                            console.log(obj);
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
                        layui.ajax('mobile/demo/insert.htm', {
                            data: $.extend({}, data.field, {
                                headUrl: headUrl
                            }),
                            success: function (resp, state, xhr) {
                                layer.msg("添加用户成功");
                                layer.close(index);
                                // 重新加载表格数据
                                page.reload();
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
        window.updateFunction = function (id) {
            layui.wind.request("front/demo/update.htm?id=" + id || '', {
                area: ['800px', '800px'],
                skin: 'layui-layer-rim',
                title: '修改实体信息',
                id: "updatePage",
                btn: ['确定'],
                yes: function () {
                    if (isUpload === true) {
                        uploadUI.upload();
                    } else {
                        $("#updateButton").click();
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
                        choose: function (obj) {
                            console.log(obj);
                            isUpload = true;
                        },
                        // 上传完成回调
                        done: function (data) {
                            headUrl = data.url;
                            $("#updateButton").click();
                            isUpload = false;
                        }
                    });

                    // 绑定 Insert Form 提交事件
                    layui.form.on("submit(updateButton)", function (data) {
                        layui.ajax('back/demo/update.htm', {
                            type: "POST", dataType: 'json',
                            data: $.extend({}, data.field, {
                                headUrl: headUrl,
                                id: id
                            }),
                            success: function () {
                                layer.msg("修改用户成功");
                                layer.close(index);
                                // 重新加载表格数据
                                page.reload();
                            },
                            error: function (response, state) {
                                layer.msg(response.responseText);
                                console.log(state);
                            }
                        });
                        return false;
                    });
                }
            });
        };

        // 删除方法
        window.deleteFunction = function (idList) {
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
                        page.reload();
                    },
                    error: function (response, state) {
                        layer.msg(response.responseText);
                        console.log(state);
                    },
                    type: "POST"
                });

            });
        };

        // 多行删除点击方法
        window.deleteAllFunction = function () {
            var idList = [], i, id = "test";
            var d = layui.table.checkStatus(id);
            for (i = 0; i < d.data.length; i++) {
                idList[i] = d.data[i].id;
            }
            window.deleteFunction(idList);
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

        // 搜索方法
        layui.form.on("submit(searchForm)", function (data) {
            var d = $.extend({}, initValue, data.field);
            page.reload(d);
            return false;
        });
    });
</script>
</body>
</html>