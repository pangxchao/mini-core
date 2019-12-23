<input type="hidden"
		id="content-page-value"
		value="${paging.page?c}" />
<input type="hidden"
		id="content-total-value"
		value="${paging.total?c}" />

<table class="layui-table"
		lay-data="{autoSort: false, limit: '${paging.limit?c}', page: false,  toolbar: '#headToolbar', defaultToolbar: []}"
		lay-filter="region-table"
		id="region-table">
	<thead>
	<tr>
		<th lay-data="{field: 'id', type: 'checkbox', fixed: 'left', width: 40}"></th>
		<th lay-data="{field: 'id'}">地区码/地区ID</th>
		<th lay-data="{field: 'name'}">地区名称</th>
		<th lay-data="{field: 'idUri'}">地区ID列表</th>
		<th lay-data="{field: 'nameUri'}">地区名称列表</th>
		<th lay-data="{field: 'regionId'}">上级地区ID</th>
		<th lay-data="{field: 'handle', fixed: 'right', toolBar: '#lineToolbar' width: '150'}">操作</th>

	</tr>
	</thead>
	<tbody>
    <#list data as item>
		<tr>
			<td>${item.id?c}</td>
			<td>${item.id}</td>
			<td>${item.name}</td>
			<td>${item.idUri}</td>
			<td>${item.nameUri}</td>
			<td>${item.regionId}</td>
			<td>${item.id?c}</td>
		</tr>
    </#list>
	</tbody>
</table>
