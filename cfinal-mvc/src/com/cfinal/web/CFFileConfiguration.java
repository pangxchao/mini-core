/**
 * Created the com.cfinal.web.CFApplicationConfiguration.java
 * @created 2017年8月22日 下午5:57:31
 * @version 1.0.0
 */
package com.cfinal.web;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfinal.util.CFJSONFile;
import com.cfinal.util.cast.CFCastUtil;
import com.cfinal.util.lang.CFClazz;
import com.cfinal.web.http.interceptor.CFInterceptor;
import com.cfinal.web.timmer.CFTimmer;

/**
 * com.cfinal.web.CFApplicationConfiguration.java
 * @author XChao
 */
public class CFFileConfiguration extends CFConfiguration {
	private JSONObject configuration;

	@Override
	protected void initialize(CFServletContext context) throws Exception {
		String configPath = "META-INF/application.config";
		InputStream stream = context.getResourceAsStream(configPath);
		this.configuration = CFJSONFile.readToObject(stream);

	}

	@Override
	protected String getEncoding(CFServletContext context) throws Exception {
		return this.configuration.getString("encoding");
	}

	@Override
	protected String getActionSuffix(CFServletContext context) throws Exception {
		return this.configuration.getString("action-suffix");
	}

	@Override
	protected long getWorkerid(CFServletContext context) throws Exception {
		return this.configuration.getLongValue("worker-id");
	}

	@Override
	protected String getLoginUrl(CFServletContext context) throws Exception {
		return this.configuration.getString("login-url");
	}

	@Override
	protected Map<String, List<Class<? extends CFInterceptor>>> getInterceptor(CFServletContext context)
		throws Exception {
		JSONObject interceptors = this.configuration.getJSONObject("interceptors");
		Map<String, List<Class<? extends CFInterceptor>>> result = new HashMap<>();
		if(interceptors != null) {
			JSONObject interceptorNames = interceptors.getJSONObject("interceptor");
			JSONObject interceptorStack = interceptors.getJSONObject("interceptor-stack");
			for (String name : interceptorStack.keySet()) {
				JSONArray array = interceptorStack.getJSONArray(name);
				List<Class<? extends CFInterceptor>> list = new ArrayList<>();
				for (int i = 0, size = array.size(); i < size; i++) {
					list.add(CFClazz.forName(interceptorNames.getString(array.getString(i)), //
						CFInterceptor.class));
				}
				result.put(name, list);
			}
		}
		return result;
	}

	@Override
	protected MultipartConfigElement getMultipartConfigElement(CFServletContext context) throws Exception {
		JSONObject multipart = this.configuration.getJSONObject("multipart-config-element");
		return new MultipartConfigElement(multipart.getString("location"), multipart.getLongValue("max-file-size"),
			multipart.getLongValue("max-request-size"), multipart.getIntValue("file-size-threshold"));
	}

	@Override
	protected Map<Class<? extends CFTimmer>, String> getTimmers(CFServletContext context) throws Exception {
		JSONObject timmers = this.configuration.getJSONObject("timmer-task");
		Map<Class<? extends CFTimmer>, String> result = new HashMap<>();
		if(timmers != null) {
			for (String clazz : timmers.keySet()) {
				result.put(CFClazz.forName(clazz, CFTimmer.class), timmers.getString(clazz));
			}
		}
		return result;
	}

	@Override
	protected Map<String, DataSource> getDataSource(CFServletContext context) throws Exception {
		JSONObject dataSource = this.configuration.getJSONObject("data-source");
		Map<String, DataSource> result = new HashMap<>();
		if(dataSource != null) {
			for (String dbName : dataSource.keySet()) {
				JSONObject resource = dataSource.getJSONObject(dbName);
				String resourseClazz = resource.getString("clazz");
				if(StringUtils.isBlank(resourseClazz)) {
					throw new RuntimeException("The clazz  of the data-source cannot be empty. ");
				}
				// dataSource.remove("clazz"); // 删除属性中的 clazz 属性， 否则在设置连接池时会报 clazz 属性找不到
				DataSource instance = CFClazz.newInstence(resourseClazz, DataSource.class);
				BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass());
				for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
					if(resource.get(property.getName()) != null && property.getWriteMethod() != null) {
						property.getWriteMethod().invoke(instance, CFCastUtil.cast( //
							resource.get(property.getName()), property.getPropertyType()));
					}
				}
				result.put(dbName, instance);
			}
		}
		return result;
	}

}
