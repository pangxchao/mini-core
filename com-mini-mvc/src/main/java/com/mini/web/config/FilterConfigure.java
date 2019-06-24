package com.mini.web.config;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.*;

import static com.mini.util.StringUtil.def;

@Singleton
public class FilterConfigure implements Serializable {
    private static final long serialVersionUID = -4631660739650897185L;
    private final List<FilterConfigureElement> elements = new ArrayList<>();


    /**
     * 添加一个过虑器
     * @param filter 过虑器
     * @return 过虑器对象
     */
    public FilterConfigureElement addFilter(Filter filter) {
        FilterConfigureElement element = new FilterConfigureElement().setFilter(filter);
        elements.add(element.setFilterName(filter.getClass().getName()));
        return element;
    }

    /**
     * Gets the value of elements.
     * @return The value of elements
     */
    public List<FilterConfigureElement> getElements() {
        return elements;
    }

    public final class FilterConfigureElement implements Serializable {
        private static final long serialVersionUID = 5240191497969156313L;
        private final List<DispatcherType> dispatcherTypes = new ArrayList<>();
        private final Set<String> urlPatterns = new HashSet<>();
        private boolean isMatchAfter = true;
        private String filterName;
        private Filter filter;


        /**
         * 添加 DispatcherType
         * @param dispatcherTypes DispatcherType
         * @return {@Code #this}
         */
        public FilterConfigureElement addDispatcherTypes(DispatcherType... dispatcherTypes) {
            Collections.addAll(this.dispatcherTypes, dispatcherTypes);
            return this;
        }


        /**
         * 添加访问路径
         * @param urlPatterns 访问路径
         * @return {@Code #this}
         */
        public FilterConfigureElement addUrlPatterns(String... urlPatterns) {
            Collections.addAll(this.urlPatterns, urlPatterns);
            return this;
        }


        /**
         * Sets the value of isMatchAfter.
         * @param matchAfter The value of isMatchAfter
         * @return {@Code #this}
         */
        public FilterConfigureElement setMatchAfter(boolean matchAfter) {
            isMatchAfter = matchAfter;
            return this;
        }

        /**
         * Sets the value of filterName.
         * @param filterName The value of filterName
         * @return {@Code #this}
         */
        public FilterConfigureElement setFilterName(String filterName) {
            this.filterName = filterName;
            return this;
        }

        /**
         * Gets the value of filter.
         * @return The value of filter
         */
        public Filter getFilter() {
            return filter;
        }

        /**
         * Sets the value of filter.
         * @param filter The value of filter
         * @return {@Code #this}
         */
        public FilterConfigureElement setFilter(Filter filter) {
            this.filter = filter;
            return this;
        }

        /**
         * 注册过虑器
         * @param context ServletContext
         */
        public void register(ServletContext context) {
            Dynamic register = context.addFilter(def(filterName, filter.getClass().getName()), filter);
            register.addMappingForUrlPatterns(EnumSet.copyOf(dispatcherTypes), isMatchAfter, urlPatterns.toArray(new String[0]));
        }
    }
}
