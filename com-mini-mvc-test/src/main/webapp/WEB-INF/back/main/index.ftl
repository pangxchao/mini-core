<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <base href="${request.contextPath}/"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <title>Mini Core 后台管理</title>
    <link type="text/css" rel="stylesheet" href="resource/layui/css/layui.css"/>
    <link type="text/css" rel="stylesheet" href="resource/mini/css/mini.css">
    <script type="text/javascript" src="resource/layui/layui.js"></script>
    <script type="text/javascript" src="resource/mini/mini.js"></script>
    <style type="text/css" media="all">
        <#include "style.css">
    </style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">
            <label class="mengyi-logo">Mini Core</label>
        </div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item">
                <a href="http://www.coursemaker.cn/" target="_blank">二级导航带图标</a>
                <dl class="layui-nav-child">
                    <dd>
                        <a href="javascript:void(0)">
                            <img src="${logo!}" class="layui-nav-img" style="width:20px; height: 20px;" alt="用户头像"/>
                            <label>带图标菜单一</label>
                        </a>
                    </dd>
                    <dd>
                        <a href="javascript:void(0)">
                            <img src="${logo!}" class="layui-nav-img" style="width:20px; height: 20px;" alt="用户头像"/>
                            <label>带图标菜单二</label>
                        </a>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="http://www.coursemaker.cn/" target="_blank">
                    二级导航不带图标
                </a>
                <dl class="layui-nav-child">
                    <dd>
                        <a href="javascript:void(0)">菜单一</a>
                    </dd>
                    <dd>
                        <a href="javascript:void(0)">菜单二</a>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="http://www.coursemaker.cn/" target="_blank" style="padding-right: 40px;">
                    一级导航
                    <span class="layui-badge" style="margin-left: 60px;">9</span>
                </a>
            </li>
        </ul>

        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:">
                    <img src="${logo!}" class="layui-nav-img" alt="用户头像"/>
                    <span class="layui-badge-dot" style="margin: -15px -20px 0;"></span>
                    <label>${userName!}</label>
                </a>
                <dl class="layui-nav-child">
                    <dd>
                        <a href="javascript:void(0)" style="min-width: 300px">基本资料</a>
                    </dd>
                    <dd>
                        <a href="javascript:void(0)" id="exit">退出</a>
                    </dd>
                </dl>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="demo">
                <!-- 首页连接 -->
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="back/main/center.htm" target="center">首页</a>
                </li>

                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:">后台模块</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="back/demo/index.htm" target="center">DEMO页面</a>
                        </dd>
                        <dd>
                            <a href="back/init/index.htm" target="center">参数管理</a>
                        </dd>
                        <dd>
                            <a href="back/user/index.htm" target="center">用户管理</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:">前台模块</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="front/demo/index.htm" target="center">DEMO页面</a>
                        </dd>
                        <dd>
                            <a href="front/user/index.htm" target="center">用户管理</a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <!-- 内容主体区域 -->
    <div class="layui-body">
        <iframe class="body-iframe" src="front/demo/index.htm" id="center" name="center"></iframe>
    </div>

    <!-- 底部固定区域 -->
    <div class="layui-footer">
        http://www.coursemaker.cn - 底部固定区域
    </div>
</div>

<script type="text/javascript">
    layui.use(['element', 'page'], function () {
        layui.element.render();
        layui.element.init(); // 更新所有模块数据

        //监听导航点击
        layui.element.on('nav(demo)', function (elem) {
            // 导航 URL节点 layui-filter="demo"
            console.log(elem)
        });

        layui.$("#exit").on("click", function (e) {
            console.log(e);
            layui.ajax({
                url: 'mobile/user/logout.htm',
                type: "GET",
                dataType: 'json',
                data: {id: '1'},
                success: function (data, state) {
                    console.log(data);
                    console.log(state);
                }
            });
        });
    });
</script>
</body>
</html>
