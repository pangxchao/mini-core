package com.mini.core.web.support.config;

import com.google.inject.Singleton;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import java.util.*;

import static java.util.Optional.of;

@Singleton
public final class RegistrationFilter implements Registration, EventListener {
	private static final String[] EMPTY = new String[0];
	private Set<DispatcherType> types = new HashSet<>();
	private Set<String> servletNames = new HashSet<>();
	private Set<String> urlPatterns = new HashSet<>();
	private boolean asyncSupported = true;
	private boolean matchAfter = true;
	private Filter filter;
	private String name;

	public RegistrationFilter(Filter filter) {
		this.filter = filter;
	}

	public void addDispatcherType(DispatcherType... types) {
		Collections.addAll(this.types, types);
	}

	public void addServletNames(String... names) {
		Collections.addAll(servletNames, names);
	}

	public void addUrlPatterns(String... patterns) {
		Collections.addAll(urlPatterns, patterns);
	}

	public void setAsyncSupported(boolean asyncSupported) {
		this.asyncSupported = asyncSupported;
	}

	public void setMatchAfter(boolean matchAfter) {
		this.matchAfter = matchAfter;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public synchronized void register(ServletContext context) {
		EnumSet<DispatcherType> type = EnumSet.copyOf(types);
		Dynamic register = context.addFilter(name, filter);
		// 以 URL Pattern 方式 注册
		of(urlPatterns).filter(v -> !v.isEmpty()).map(v -> //
			v.toArray(EMPTY)).ifPresent(v -> { //
			register.addMappingForUrlPatterns( //
				type, matchAfter, v);
		});
		// 以Servlet Name 方式注册
		of(servletNames).filter(v -> !v.isEmpty()).map(v -> //
			v.toArray(EMPTY)).ifPresent(v -> { //
			register.addMappingForServletNames( //
				type, matchAfter, v);
		});
		register.setAsyncSupported(asyncSupported);
	}
}
