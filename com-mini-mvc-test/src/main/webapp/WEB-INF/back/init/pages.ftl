<table lay-data="{url: 'back/init/pages.htm', autoSort: false, toolbar: '#headToolbar', defaultToolbar: [], page: true}"
		class="layui-table"
		lay-filter="init-table"
		id="init-table">
	<thead>
	<tr>
		<th lay-data="{type: 'checkbox', fixed: 'left', width: 40}"></th>
		<th lay-data="{field: 'id', templet: '#id_template', width: 200}">参数键</th>
		<th lay-data="{field: 'value', templet: '#value_template', width: 200}">参数值</th>
		<th lay-data="{field: 'remarks', templet: '#remarks_template', width: 200}">参数说明</th>
		<th lay-data="{fixed: 'right', toolbar: '#lineToolbar', width: '150'}">操作</th>
	</tr>
	</thead>
</table>

<script type="text/html"
		id="id_template">
	{{= d.id}}
</script>
<script type="text/html"
		id="value_template">
	{{= d.value}}
</script>
<script type="text/html"
		id="remarks_template">
	{{= d.remarks}}
</script>

<!-- 头部工具拦模板 -->
<script type="text/html"
		id="headToolbar">
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm"
				lay-event="add">添加
		</button>
		<button class="layui-btn layui-btn-sm"
				lay-event="delete">删除
		</button>
	</div>
</script>

<!-- 每行的编辑工具拦模板 -->
<script type="text/html"
		id="lineToolbar">
	<a class="layui-btn layui-btn-xs"
			lay-event="edit">编辑
	</a>
	<a class="layui-btn layui-btn-danger layui-btn-xs"
			lay-event="del">删除
	</a>
</script>