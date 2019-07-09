package com.mini.web.config;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.io.Serializable;
import java.util.*;

import static com.mini.util.StringUtil.def;

@Singleton
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
         * Gets the value of dispatcherTypes.
         * @return The value of dispatcherTypes
         */
        public List<DispatcherType> getDispatcherTypes() {
            return dispatcherTypes;
        }

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
         * Gets the value of urlPatterns.
         * @return The value of urlPatterns
         */
        public String[] getUrlPatterns() {
            return urlPatterns.toArray(new String[0]);
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
         * Gets the value of filterClass.
         * @return The value of filterClass
         */
        public Class<? extends Filter> getFilterClass() {
            return filterClass;
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
         * Gets the value of isMatchAfter.
         * @return The value of isMatchAfter
         */
        public boolean isMatchAfter() {
            return isMatchAfter;
        }

        /**
         * The value of matchAfter
         * @param matchAfter The value of matchAfter
         * @return {@Code this}
         */
        public FilterElement setMatchAfter(boolean matchAfter) {
            isMatchAfter = matchAfter;
            return this;
        }

        /**
         * Gets the value of filterName.
         * @return The value of filterName
         */
        @Nonnull
        public String getFilterName() {
            Objects.requireNonNull(filterClass, "FilterClass can not be null");
            return def(filterName, filterClass.getName());
        }

        /**
         * The value of filterName
         * @param filterName The value of filterName
         * @return {@Code this}
         */
        public FilterElement setFilterName(String filterName) {
            this.filterName = filterName;
            return this;
        }
    }
}
