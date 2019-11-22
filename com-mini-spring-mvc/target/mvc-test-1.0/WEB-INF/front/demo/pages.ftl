<input type="hidden" id="content-page-value" value="${paging.page?c}"/>
<input type="hidden" id="content-total-value" value="${paging.total?c}"/>

<table class="layui-table"
       lay-data="{autoSort: false, limit: '${paging.limit?c}', page: false,  toolbar: '#toolbarDemo', defaultToolbar: []}"
       lay-filter="test" id="test">
    <thead>
    <tr>
        <th lay-data="{field: 'id', type: 'checkbox', fixed: 'left', width: 40}"></th>
        <th lay-data="{field: 'name', width: 120}">用户名</th>
        <th lay-data=" {field: 'phone', width: 120}">手机号</th>
        <th lay-data=" {field: 'phoneAuth', width: 120}">认证手机号</th>
        <th lay-data=" {field: 'fullName', width: 120}">姓名</th>
        <th lay-data="{field: 'email', width: 180}">邮箱</th>
        <th lay-data=" {field: 'emailAuth',width: 120}">认证邮箱</th>
        <th lay-data=" {field: 'fullHeadUrl'}">用户头像</th>
        <th lay-data=" {field: 'regionNameUri'}">所属地区</th>
        <th lay-data="{field: 'createTime', width: 150}">创建时间</th>
        <th lay-data="{field: 'handle', fixed: 'right', minWidth: '250'}">操作</th>

    </tr>
    </thead>
    <tbody>
    <#list userList as item>
        <tr>
            <td>${item.id?c}</td>
            <td>${item.name}</td>
            <td>${item.phone}</td>
            <td>
                <#if item.phoneAuth == 1 >
                    是
                <#else>
                    否
                </#if>
            </td>
            <td>${item.fullName}</td>
            <td>${item.email}</td>
            <td>
                <#if item.emailAuth == 1 >
                    是
                <#else>
                    否
                </#if>
            </td>
            <td>
                <#if item.fullHeadUrl?? && item.fullHeadUrl != '' >
                    <img src="${item.fullHeadUrl}" alt="${item.fullHeadUrl}"/>
                </#if>
            </td>
            <td>${item.regionNameUri}</td>
            <td>${item.createTime?date}</td>
            <td>
                <a class="layui-btn layui-btn-primary layui-btn-xs" href="https://www.layui.com/demo/form.html?id=${item.id?c}"
                   target="_blank">
                    查看
                </a>
                <a class="layui-btn layui-btn-xs" href="javascript:window.updateFunction('${item.id?c}')">
                    编辑
                </a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" href="javascript:window.deleteFunction(['${item.id?c}'])">
                    删除
                </a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
