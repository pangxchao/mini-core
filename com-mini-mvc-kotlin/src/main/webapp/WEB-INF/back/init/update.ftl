<div class=" mini-main">
    <form class="layui-form mini-form" action="" id="updateForm" lay-filter="updateForm">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">参数键</label>
                <div class="layui-input-inline">
                    <input type="text" name="id" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">参数值</label>
                <div class="layui-input-inline">
                    <input type="text" name="value" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">参数说明</label>
                <div class="layui-input-inline">
                    <input type="text" name="remarks" class="layui-input"/>
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