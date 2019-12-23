layui.define(['jquery', 'layer'], function (exports) {
	var $ = layui.$, layer = layui.layer;

	var append_array = function (formData, key, value) {
		if (!layui.jquery.isArray(value)) {
			formData.append(key, value);
			return;
		}
		$.each(value, function (index, val) {
			formData.append(key, val);
		});
	};

	var append_all = function (formData, dataArray) {
		$.each(dataArray || [], function (key, val) {
			append_array(formData, key, val);
		});
		return formData;
	};

	var result = function (url, options) {
		layui.$.ajax(layui.$.extend({
			error: function (response) {
				var t = response.responseText;
				layer.alert(t);
			},
			traditional: true,
			dataType: 'json'
		}, options, {url: url}));
	};

	result.get = function (url, data, options) {
		result(url, $.extend({}, options, {
			type: "GET",
			data: data
		}));
	};

	result.post = function (url, data, options) {
		result(url, $.extend({}, options, {
			type: "POST",
			data: data
		}));
	};

	result.form = function (form, url, options) {
		var data = new FormData($(form).get(0));
		data = append_all(data, options.data);
		result(url, $.extend({}, options, {
			contentType: false,
			processData: false,
			type: "POST",
			data: data
		}));
	};
	// 返回对象
	exports('ajax', result);
});