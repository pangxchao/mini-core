<div class=" mini-main">
	<form class="layui-form mini-form"
			action=""
			id="updateForm"
			lay-filter="updateForm">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">地区码/地区ID</label>
				<div class="layui-input-inline">
					<input type="text"
							name="id"
							class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">地区名称</label>
				<div class="layui-input-inline">
					<input type="text"
							name="name"
							class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">地区ID列表</label>
				<div class="layui-input-inline">
					<input type="text"
							name="idUri"
							class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">地区名称列表</label>
				<div class="layui-input-inline">
					<input type="text"
							name="nameUri"
							class="layui-input" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">上级地区ID</label>
				<div class="layui-input-inline">
					<input type="text"
							name="regionId"
							class="layui-input" />
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-input-block mini-input-block">
				<button class="layui-btn layui-btn-sm"
						lay-submit
						lay-filter="updateButton"
						style="display: none;"
						id="updateButton">立即提交
				</button>
			</div>
		</div>
	</form>
</div>