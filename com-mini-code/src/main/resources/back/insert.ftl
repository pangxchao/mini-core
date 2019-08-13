<div class=" mini-main">
    <form class="layui-form mini-form" action="" id="insertForm" lay-filter="insertForm">
        <#list fieldList as item>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">${item.remarks}</label>
                    <div class="layui-input-inline">
                        <input type="text" name="${item.name}" class="layui-input"/>
                    </div>
                </div>
            </div>
        </#list>

        <div class="layui-form-item">
            <div class="layui-input-block mini-input-block">
                <button class="layui-btn layui-btn-sm" lay-submit lay-filter="insertButton"
                        style="display: none;" id="insertButton">立即提交
                </button>
            </div>
        </div>
    </form>
</div>