<input type="hidden" id="content-page-value" value="${r'${paging.page?c}'}"/>
<input type="hidden" id="content-total-value" value="${r'${paging.total?c}'}"/>

<table class="layui-table" lay-data="{autoSort: false, limit: '${r'${paging.limit?c}'}', page: false,  toolbar: '#headToolbar', defaultToolbar: []}"
       lay-filter="${name}-table" id="${name}-table">
    <thead>
    <tr>
        <th lay-data="{field: 'id', type: 'checkbox', fixed: 'left', width: 40}"></th>
        <#list fieldList as item>
            <th lay-data="{field: '${item.name}', width: 200}">${item.remarks}</th>
        </#list>
        <th lay-data="{field: 'handle', fixed: 'right', toolbar: '#lineToolbar', width: '150'}">操作</th>

    </tr>
    </thead>
    <tbody>
    ${r'<#list data as item>'}
    <tr>
        <td>${r'${item.id?c}'}</td>
        <#list fieldList as item>
            <td>${r'$'}${r'{'}item.${item.name!}${r'}'}</td>
        </#list>
        <td>${r'${item.id?c}'}</td>
    </tr>
    ${r'</#list>'}
    </tbody>
</table>
