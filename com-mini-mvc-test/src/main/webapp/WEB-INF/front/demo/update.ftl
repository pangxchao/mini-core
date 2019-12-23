<div class=" mini-main">
	<form class="layui-form mini-form"
			action=""
			id="updateForm"
			lay-filter="updateForm">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-inline">
					<input type="text"
							name="name"
							class="layui-input"
							value="${user.name!}" />
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-inline ">
					<input type="text"
							name="phone"
							class="layui-input"
							value="${user.phone!}" />
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">认证手机号</label>
				<div class="layui-input-block">
                    <#if user.phoneAuth == 1 >
						<input type="radio"
								name="phoneAuth"
								value="1"
								title="是"
								checked
								lay-filter="phoneAuth" />
						<input type="radio"
								name="phoneAuth"
								value="0"
								title="否"
								lay-filter="phoneAuth" />
                    <#else >
						<input type="radio"
								name="phoneAuth"
								value="1"
								title="是"
								lay-filter="phoneAuth" />
						<input type="radio"
								name="phoneAuth"
								value="0"
								title="否"
								checked
								lay-filter="phoneAuth" />
                    </#if>
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-inline ">
					<input type="text"
							name="fullName"
							class="layui-input"
							value="${user.fullName!}" />
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-inline ">
					<input type="text"
							name="email"
							class="layui-input"
							value="${user.email!}" />
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">认证邮箱</label>
				<div class="layui-input-block">
                    <#if user.emailAuth?? && user.emailAuth == 1  >
						<input type="checkbox"
								name="emailAuth"
								value="1"
								title="认证邮箱"
								lay-filter="emailAuth"
								checked />
                    <#else >
						<input type="checkbox"
								name="emailAuth"
								value="1"
								title="认证邮箱"
								lay-filter="emailAuth" />
                    </#if>
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">用户头像</label>
				<div class="layui-input-block">
					<input type="hidden"
							name="headUrl"
							value="${user.headUrl!}" />
					<button type="button"
							class="layui-btn layui-btn-sm layui-btn-normal"
							id="headUrlFile">选择文件
					</button>
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label mini-form-label">所属地区</label>
			<div class="layui-input-inline">
				<select name="provinceEdit"
						id="provinceEdit"
						url="mobile/region/list.htm?id="
						child="cityEdit"
						def="${province!}"
						placeholder="请选择省">
					<option value="">请选择省</option>
					<option value="浙江"
							selected="">浙江省
					</option>
					<option value="你的工号"
							method>江西省
					</option>
					<option value="你最喜欢的老师">福建省</option>
				</select>
			</div>
			<div class="layui-input-inline">
				<select name="cityEdit"
						id="cityEdit"
						url="mobile/region/list.htm?id="
						def="${city!}"
						child="regionId"
						placeholder="请选择市">
					<option value="">请选择市</option>
					<option value="杭州">杭州</option>
					<option value="宁波"
							disabled="">宁波
					</option>
					<option value="温州">温州</option>
					<option value="温州">台州</option>
					<option value="温州">绍兴</option>
				</select>
			</div>
			<div class="layui-input-inline">
				<select name="regionId"
						id="regionId"
						url="mobile/region/list.htm?id="
						def="${regionId!}"
						placeholder="请选择县/区">
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
				<button class="layui-btn layui-btn-sm"
						lay-submit
						style="display: none;"
						lay-filter="updateButton"
						id="updateButton">立即提交
				</button>
			</div>
		</div>
	</form>
</div>
