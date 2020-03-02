package com.mini.core.web.support;

import com.mini.core.holder.web.*;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.argument.ArgumentResolver;
import com.mini.core.web.interceptor.ActionInterceptor;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.model.IModel;
import com.mini.core.web.util.ResponseCode;
import com.mini.core.web.view.PageViewResolver;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;

import static com.mini.core.validate.ValidateUtil.*;

public interface ActionSupportProxy {
	/**
	 * 获取Controller类Class对象
	 * @return Class对象
	 */
	@Nonnull
	Class<?> getClazz();

	/**
	 * 获取目标方法对象
	 * @return 目标方法对象
	 */
	@Nonnull
	Method getMethod();

	/**
	 * 获取数据模型实现类型
	 * @param resolver 视图解析器
	 * @return 数据模型实现类型
	 */
	@Nonnull
	IModel<?> getModel(PageViewResolver resolver);

	/**
	 * 获取控制器支持的方法
	 * @return 控制器支持的方法
	 */
	@Nonnull
	Action.Method[] getSupportMethod();

	/**
	 * 获取所有拦截器对象
	 * @return 拦截器对象
	 */
	@Nonnull
	List<ActionInterceptor> getInterceptors();

	/**
	 * 获取目标方法的所有参数信息
	 * @return 所有参数信息
	 */
	@Nonnull
	MiniParameter[] getParameters();

	/**
	 * 获取参数处理器列表
	 * @return 参数处理器列表
	 */
	@Nonnull
	ParameterHandler[] getParameterHandlers();

	/**
	 * 获取 Action 视图路径
	 * @return Action 视图路径
	 */
	String getViewPath();

	/**
	 * 获取方法的请求路径
	 * @return 方法请求路径
	 */
	String getRequestUri();

	final class ParameterHandler implements EventListener, ResponseCode {
		private final IsMobilePhone isMobilePhone;
		private final ArgumentResolver resolver;
		private final MiniParameter parameter;
		private final IsNotBlank isNotBlank;
		private final IsNotNull isNotNull;
		private final IsRequire isRequire;
		private final IsChinese isChinese;
		private final IsLetter isLetter;
		private final IsMobile isMobile;
		private final IsNumber isNumber;
		private final IsIdCard isIdCard;
		private final IsPhone isPhone;
		private final IsEmail isEmail;
		private final IsRegex isRegex;
		private final Is $is;

		public ParameterHandler(@Nonnull ArgumentResolver resolver,
				@Nonnull MiniParameter parameter) {
			isMobilePhone = parameter.getAnnotation(IsMobilePhone.class);
			isNotBlank = parameter.getAnnotation(IsNotBlank.class);
			isNotNull = parameter.getAnnotation(IsNotNull.class);
			isRequire = parameter.getAnnotation(IsRequire.class);
			isChinese = parameter.getAnnotation(IsChinese.class);
			isLetter = parameter.getAnnotation(IsLetter.class);
			isMobile = parameter.getAnnotation(IsMobile.class);
			isNumber = parameter.getAnnotation(IsNumber.class);
			isIdCard = parameter.getAnnotation(IsIdCard.class);
			isPhone = parameter.getAnnotation(IsPhone.class);
			isEmail = parameter.getAnnotation(IsEmail.class);
			isRegex = parameter.getAnnotation(IsRegex.class);
			$is = parameter.getAnnotation(Is.class);
			this.parameter = parameter;
			this.resolver = resolver;
		}

		public final Object getValue(ActionInvocation invocation) {
			var value = resolver.getValue(parameter, invocation);
			// 手机号码/电话号码验证
			if (isMobilePhone != null && (isMobilePhone.require() || value != null)) {
				isMobilePhone((String) value, isMobilePhone.error(),  //
						isMobilePhone.message());
			}
			// 电话号码验证
			if (isPhone != null && (isPhone.require() || value != null)) {
				isLetter((String) value, isPhone.error(), isPhone.message());
			}
			// 用户名验证
			if (isRequire != null && (isRequire.require() || value != null)) {
				isRequire((String) value, isRequire.error(), isRequire.message());
			}
			// 中文验证
			if (isChinese != null && (isChinese.require() || value != null)) {
				isChinese((String) value, isChinese.error(), isChinese.message());
			}
			// 英文验证
			if (isLetter != null && (isLetter.require() || value != null)) {
				isLetter((String) value, isLetter.error(), isLetter.message());
			}
			// 手机号验证
			if (isMobile != null && (isMobile.require() || value != null)) {
				isLetter((String) value, isMobile.error(), isMobile.message());
			}
			// 数字验证
			if (isNumber != null && (isNumber.require() || value != null)) {
				isLetter((String) value, isNumber.error(), isNumber.message());
			}
			// 身份证号码验证
			if (isIdCard != null && (isIdCard.require() || value != null)) {
				isLetter((String) value, isIdCard.error(), isIdCard.message());
			}
			// 电话号码验证
			if (isPhone != null && (isPhone.require() || value != null)) {
				isLetter((String) value, isPhone.error(), isPhone.message());
			}
			// 邮箱验证
			if (isEmail != null && (isEmail.require() || value != null)) {
				isLetter((String) value, isEmail.error(), isEmail.message());
			}
			// 正则表达式
			if (isRegex != null && (isRegex.require() || value != null)) {
				isRegex((String) value, isRegex.regex(), isRegex.error(),//
						isRegex.message());
			}
			// 自定义验证
			if ($is != null && ($is.require() || value != null)) {
				is(true, $is.error(), $is.message());
			}
			// 字符串非空验证
			if (Objects.nonNull(ParameterHandler.this.isNotBlank)) {
				isNotBlank((String) value, isNotBlank.error(), //
						isNotBlank.message());
			}
			// 对象非空验证
			if (Objects.nonNull(ParameterHandler.this.isNotNull)) {
				isNotNull(value, isNotNull.error(), //
						isNotNull.message());
			}
			return value;
		}
	}
}
