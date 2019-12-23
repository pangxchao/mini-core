<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<base href="${request.contextPath}/" />
	<meta name="renderer"
			content="webkit">
	<meta http-equiv="X-UA-Compatible"
			content="IE=edge,chrome=1">
	<meta http-equiv="Content-Type"
			content="text/html; charset=utf-8" />
	<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>WebSocket 测试</title>
	<link type="text/css"
			rel="stylesheet"
			href="resource/layui/css/layui.css" />
	<link type="text/css"
			rel="stylesheet"
			href="resource/mini/css/mini.css">
	<script type="text/javascript"
			src="resource/layui/layui.js"></script>
	<script type="text/javascript"
			src="resource/mini/mini.js"></script>
</head>
<body>
<div class="layui-main mini-main">
	<blockquote class="layui-elem-quote">用户信息管理</blockquote>
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm"
				id="send_service">向服务器发送消息
		</button>
		<button class="layui-btn layui-btn-sm"
				id="service_send">服务器向本地发送消息
		</button>
	</div>
</div>
<script type="text/javascript">
	layui.use(['ajax', 'jquery', 'layer'], function () {
		var socket = new WebSocket("ws://127.0.0.1/mini/socket/${username}");
		console.log(socket);

		// 连接成功的回调
		socket.onopen = function (evt) {
			console.log("已连接");
			console.log(evt);
		};

		// 接收服务器发送过来的消息
		socket.onmessage = function (evt) {
			console.log("这是服务器发过来的消息？");
			console.log(evt);
		};

		// 连接已关闭
		socket.onclose = function (evt) {
			console.log("已关闭");
			console.log(evt);
		};

		// 通信过程中出现错误
		socket.onerror = function (evt) {
			console.log("出错啦");
			console.log(evt);
		};

		// 绑定按钮点击事件
		layui.$("#send_service").on("click", function () {
			layui.layer.prompt({
				title: '请输入消息内容',
				formType: 2
			}, function (value, i) {
				layui.layer.close(i);
				socket.send(value);
			});
		});

		layui.$("#service_send").on("click", function () {
			layui.layer.prompt({
				title: '请输入消息内容',
				formType: 2
			}, function (value, i) {
				layui.ajax("web_socket/sendMessage.htm", {
					success: function (data, state) {
						console.log("---成功--" + state)
					},
					data: {
						userName: '${username}',
						message: value
					}
				});
				layui.layer.close(i);
			});
		});
	});
</script>
</body>
</html>