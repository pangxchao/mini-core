<div class=" mini-main">
    <form class="layui-form mini-form" action="" id="insertForm" lay-filter="insertForm">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                    <input type="text" name="name" class="layui-input"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item" id="passwordEl">
            <div class="layui-inline">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-inline ">
                    <input type="password" name="password" class="layui-input"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline ">
                    <input type="text" name="phone" class="layui-input"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">认证手机号</label>
                <div class="layui-input-block">
                    <input type="radio" name="phoneAuth" value="1" title="是" checked lay-filter="phoneAuth"/>
                    <input type="radio" name="phoneAuth" value="0" title="否" lay-filter="phoneAuth"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-inline ">
                    <input type="text" name="fullName" class="layui-input"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline ">
                    <input type="text" name="email" class="layui-input"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">认证邮箱</label>
                <div class="layui-input-block">
                    <input type="checkbox" name="emailAuth" value="1" title="认证邮箱" lay-filter="emailAuth" checked/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">用户头像</label>
                <div class="layui-input-block">
                    <input type="hidden" name="headUrl" value=""/>
                    <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" id="headUrlFile">选择文件</button>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label mini-form-label">所属地区</label>
            <div class="layui-input-inline">
                <select name="provinceEdit" id="provinceEdit" url="mobile/region/list.htm?id=" child="cityEdit" def="500000" placeholder="请选择省">
                    <option value="">请选择省</option>
                    <option value="浙江" selected="">浙江省</option>
                    <option value="你的工号" method>江西省</option>
                    <option value="你最喜欢的老师">福建省</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <select name="cityEdit" id="cityEdit" url="mobile/region/list.htm?id=" child="regionId" def="500100" placeholder="请选择市">
                    <option value="">请选择市</option>
                    <option value="杭州">杭州</option>
                    <option value="宁波" disabled="">宁波</option>
                    <option value="温州">温州</option>
                    <option value="温州">台州</option>
                    <option value="温州">绍兴</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <select name="regionId" id="regionId" url="mobile/region/list.htm?id=" def="500112" placeholder="请选择县/区">
                    <option value="">请选择县/区</option>
                    <option value="西湖区">西湖区</option>
                    <option value="余杭区">余杭区</option>
                    <option value="拱墅区">临安市</option>
                </select>
            </div>
            <div class="layui-form-mid layui-word-aux">此处演示联动排版和功能</div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block mini-input-block">
                <button class="layui-btn layui-btn-sm" lay-submit style="display: none;"
                        lay-filter="insertButton" id="insertButton">立即提交
                </button>
            </div>
        </div>
    </form>
</div>
