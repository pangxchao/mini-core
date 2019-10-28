<#--noinspection HtmlUnknownAttribute,FtlReferencesInspection-->
<table lay-data="{url: 'back/${name}/pages.htm', autoSort: false, toolbar: '#headToolbar', defaultToolbar: [], page: true}"
        <#--noinspection FtlReferencesInspection-->
       class="layui-table" lay-filter="${name}-table" id="${name}-table">
    <thead>
    <tr>
        <th lay-data="{type: 'checkbox', fixed: 'left', width: 40}"></th>
        <#--noinspection FtlReferencesInspection-->
        <#list fieldList as item>
        <#--noinspection HtmlUnknownAttribute-->
            <th lay-data="{field: '${item.name}', templet: '#${item.name}_template', width: 200}">${item.remarks}</th>
        </#list>
        <#--noinspection HtmlUnknownAttribute-->
        <th lay-data="{fixed: 'right', toolbar: '#lineToolbar', width: '150'}">操作</th>
    </tr>
    </thead>
</table>

<#--noinspection FtlReferencesInspection-->
<#list fieldList as item>
    <script type="text/html" id="${item.name}_template">
        {{= d.${item.name}}}
    </script>

</#list>

<!-- 头部工具拦模板 -->
<script type="text/html" id="headToolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
        <button class="layui-btn layui-btn-sm" lay-event="delete">删除</button>
    </div>
</script>

<!-- 每行的编辑工具拦模板 -->
<script type="text/html" id="lineToolbar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>