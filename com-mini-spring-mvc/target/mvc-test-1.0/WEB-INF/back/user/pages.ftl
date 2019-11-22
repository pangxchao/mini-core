<input type="hidden" id="content-page-value" value="${paging.page?c}"/>
<input type="hidden" id="content-total-value" value="${paging.total?c}"/>

<table class="layui-table"
       lay-data="{autoSort: false, limit: '${paging.limit?c}', page: false,  toolbar: '#headToolbar', defaultToolbar: []}"
       lay-filter="user-table" id="user-table">
    <thead>
    <tr>
        <th lay-data="{field: 'id', type: 'checkbox', fixed: 'left', width: 40}"></th>
        <th lay-data="{field: 'id'}">用户ID</th>
        <th lay-data="{field: 'name'}">用户名</th>
        <th lay-data="{field: 'password'}">MD5(密码)</th>
        <th lay-data="{field: 'phone'}">用户手机号</th>
        <th lay-data="{field: 'phoneAuth'}">0-未认证，1-已谁</th>
        <th lay-data="{field: 'fullName'}">用户姓名</th>
        <th lay-data="{field: 'email'}">用户邮箱地址</th>
        <th lay-data="{field: 'emailAuth'}">0-未认证，1-已认证</th>
        <th lay-data="{field: 'headUrl'}">用户头像地址</th>
        <th lay-data="{field: 'regionId'}">用户所属地区ID</th>
        <th lay-data="{field: 'createTime'}">用户注册时间</th>
        <th lay-data="{field: 'handle', fixed: 'right', toolBar: '#lineToolbar' width: '150'}">操作</th>

    </tr>
    </thead>
    <tbody>
    <#list data as item>
        <tr>
            <td>${item.id?c}</td>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.password}</td>
            <td>${item.phone}</td>
            <td>${item.phoneAuth}</td>
            <td>${item.fullName}</td>
            <td>${item.email}</td>
            <td>${item.emailAuth}</td>
            <td>${item.headUrl}</td>
            <td>${item.regionId}</td>
            <td>${item.createTime}</td>
            <td>${item.id?c}</td>
        </tr>
    </#list>
    </tbody>
</table>
