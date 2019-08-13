<div class=" mini-main">
    <form class="layui-form mini-form" action="" id="updateForm" lay-filter="updateForm">
                    <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户ID</label>
                    <div class="layui-input-inline">
                        <input type="text" name="id" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">MD5(密码)</label>
                    <div class="layui-input-inline">
                        <input type="text" name="password" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户手机号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">0-未认证，1-已谁</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phoneAuth" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fullName" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户邮箱地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="email" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">0-未认证，1-已认证</label>
                    <div class="layui-input-inline">
                        <input type="text" name="emailAuth" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户头像地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="headUrl" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户所属地区ID</label>
                    <div class="layui-input-inline">
                        <input type="text" name="regionId" class="layui-input"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户注册时间</label>
                    <div class="layui-input-inline">
                        <input type="text" name="createTime" class="layui-input"/>
                    </div>
                </div>
            </div>

        <div class="layui-form-item">
            <div class="layui-input-block mini-input-block">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="updateButton"
                        style="display: none;" id="updateButton">立即提交
                </button>
            </div>
        </div>
    </form>
</div>