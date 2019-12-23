<table class="layui-table"
		id="test"
		lay-data="{url: 'back/demo/pages.htm', autoSort: false, toolbar: '#toolbarDemo', defaultToolbar: [], page: true}"
		lay-filter="test">
	<thead>
	<tr>
		<th lay-data="{type: 'checkbox', fixed: 'left', width: 40}"></th>
		<th lay-data="{field: 'name', width: 120, sort: true}">用户名</th>
		<th lay-data=" {field: 'phone', width: 120}">手机号</th>
		<th lay-data=" {field: 'phoneAuth', width: 120,  templet: '#auto_phone'}">认证手机号</th>
		<th lay-data=" {field: 'fullName', width: 120}">姓名</th>
		<th lay-data="{field: 'email', width: 180}">邮箱</th>
		<th lay-data=" {field: 'emailAuth',width: 120,  templet: '#auto_email'}">认证邮箱</th>
		<th lay-data=" {field: 'emailAuth', templet: '#head_url'}">用户头像</th>
		<th lay-data=" {field: 'regionNameUri'}">所属地区</th>
		<th lay-data="{field: 'createTime', width: 150}">创建时间</th>
		<th lay-data="{fixed: 'right', align: 'left', toolbar: '#lineBar', minWidth: '250'}"></th>
	</tr>
	</thead>
</table>

<!-- 该模板用于对每行数据的操作 -->
<script type="text/html"
		id="auto_phone">
	{{# if(d.phoneAuth === '1') { }}
	是
	{{# } else { }}
	否
	{{# } }}
</script>

<script type="text/html"
		id="auto_email">
	{{# if(d.emailAuth === '1') { }}
	是
	{{# } else { }}
	否
	{{# } }}
</script>

<script type="text/html"
		id="head_url">
	{{# if(d.fullHeadUrl && d.fullHeadUrl !== '') { }}
	<img src="{{=d.fullHeadUrl}}"
			alt="{{=d.fullHeadUrl}}" />
	{{# } }}
</script>


<!-- 头部工具拦演示 -->
<script type="text/html"
		id="toolbarDemo">
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm"
				lay-event="add">添加
		</button>
		<button class="layui-btn layui-btn-sm"
				lay-event="delete">删除
		</button>
	</div>
</script>

<!-- 该模板用于对每行数据的操作 -->
<!-- 该模板的条件语句只为演示不同情况显示不同操作 -->
<script type="text/html"
		id="lineBar">
	<a class="layui-btn layui-btn-primary layui-btn-xs"
			lay-event="detail"
			href="https://www.layui.com/demo/form.html"
			target="_blank">查看
	</a>
	<a class="layui-btn layui-btn-xs"
			lay-event="edit">编辑
	</a>
	<a class="layui-btn layui-btn-danger layui-btn-xs"
			lay-event="del">删除
	</a>
	{{# if(d.phoneAuth !== '1') { }}
	<a class="layui-btn layui-btn-danger layui-btn-xs"
			lay-event="auth">认证手机
	</a>
	{{# } }}
</script>