<!DOCTYPE html>
<html lang="en" >
<head >
    <meta charset="UTF-8" >
    <base href="${request.contextPath}/" />
    <meta name="renderer" content="webkit" >
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" >
    <title >hello.ftl</title >
    <link type="text/css" rel="stylesheet" href="resources/layui/css/layui.css" />
    <script type="text/javascript" src="resources/layui/jquery-3.3.1.min.js" ></script >
    <script type="text/javascript" src="resources/layui/layui.js" ></script >
    <script type="text/javascript" src="resources/mini/mini.js" ></script >
</head >
<body >
这下面显示分页的数据
<div id="content" >

</div >

<script type="text/javascript" >
    layui.use(['page'], function () {
        var page = layui.page("#content", {
            elem: "content-page-button",
            count: 200,
            limit: 10,
            curr: 3,
            groups: 5,
            limits: [10, 20, 30, 40, 50],
            layout: ['limit', 'count', 'prev', 'templates.page', 'next', 'refresh', 'skip'],
            onload: function (p) {
                // 页面每次加载完成时回调
            }
        });
        page.load({
            url: 'front/user/page.htm',
            data: {},
            //form: 'formId',
            complete: function () {
            }
        });
    });
</script >
</body >
</html >