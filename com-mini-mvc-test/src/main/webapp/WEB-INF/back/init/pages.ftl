<input type="hidden" id="content-page-value" value="${paging.page?c}"/>
<input type="hidden" id="content-total-value" value="${paging.total?c}"/>

<table class="layui-table" lay-data="{autoSort: false, limit: '${paging.limit?c}', page: false,  toolbar: '#headToolbar', defaultToolbar: []}"
       lay-filter="init-table" id="init-table">
    <thead>
    <tr>
        <th lay-data="{field: 'id', type: 'checkbox', fixed: 'left', width: 40}"></th>
            <th lay-data="{field: 'id', width: 200}">参数键</th>
            <th lay-data="{field: 'value', width: 200}">参数值</th>
            <th lay-data="{field: 'remarks', width: 200}">参数说明</th>
        <th lay-data="{field: 'handle', fixed: 'right', toolbar: '#lineToolbar', width: '150'}">操作</th>

    </tr>
    </thead>
    <tbody>
    <#list data as item>
    <tr>
        <td>${item.id?c}</td>
            <td>${item.id}</td>
            <td>${item.value}</td>
            <td>${item.remarks}</td>
        <td>${item.id?c}</td>
    </tr>
    </#list>
    </tbody>
</table>
