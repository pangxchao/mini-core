package com.mini.web.config;

import com.mini.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.*;

import static java.util.EnumSet.copyOf;

@Component
public final class FilterConfigure implements Serializable {
    private static final long serialVersionUID = -4631660739650897185L;
    private final List<FilterElement> elements = new ArrayList<>();


    /**
     * 添加一个过虑器
     * @param filterClass 过虑器
     * @return 过虑器对象
     */
    public FilterElement addFilter(Class<? extends Filter> filterClass) {
        FilterElement element = new FilterElement();
        element.setFilterName(filterClass.getName());
        element.setFilterClass(filterClass);
        elements.add(element);
        return element;
    }

    /**
     * Gets the value of elements.
     * @return The value of elements
     */
    public List<FilterElement> getElements() {
        return elements;
    }

    public final class FilterElement implements Serializable {
        private static final long serialVersionUID = 5240191497969156313L;
        private final List<DispatcherType> dispatcherTypes = new ArrayList<>();
        private final Set<String> urlPatterns = new HashSet<>();
        private Class<? extends Filter> filterClass;
        private boolean isMatchAfter = true;
        private String filterName;


        /**
         * 添加 DispatcherType
         * @param dispatcherTypes DispatcherType
         * @return {@Code #this}
         */
        public FilterElement addDispatcherTypes(DispatcherType... dispatcherTypes) {
            Collections.addAll(this.dispatcherTypes, dispatcherTypes);
            return this;
        }


        /**
         * 添加访问路径
         * @param urlPatterns 访问路径
         * @return {@Code #this}
         */
        public FilterElement addUrlPatterns(String... urlPatterns) {
            Collections.addAll(this.urlPatterns, urlPatterns);
            return this;
        }


        /**
         * Sets the value of isMatchAfter.
         * @param matchAfter The value of isMatchAfter
         * @return {@Code #this}
         */
        public FilterElement setMatchAfter(boolean matchAfter) {
            isMatchAfter = matchAfter;
            return this;
        }

        /**
         * Sets the value of filterName.
         * @param filterName The value of filterName
         * @return {@Code #this}
         */
        public FilterElement setFilterName(String filterName) {
            this.filterName = filterName;
            return this;
        }

        /**
         * The value of filterClass
         * @param filterClass The value of filterClass
         * @return {@Code this}
         */
        public FilterElement setFilterClass(Class<? extends Filter> filterClass) {
            this.filterClass = filterClass;
            return this;
        }

        /**
         * 注册过虑器
         * @param context ServletContext
         */
        public void register(ServletContext context, ApplicationContext applicationContext) {
            String name = StringUtil.def(filterName, filterClass.getName());
            Filter filter = applicationContext.getBean(name, Filter.class);
            FilterRegistration.Dynamic register = context.addFilter(name, filter);
            register.addMappingForUrlPatterns(copyOf(dispatcherTypes), isMatchAfter, getUrlPatterns());
        }

        private String[] getUrlPatterns() {
            return urlPatterns.toArray(new String[0]);
        }
    }
}
