package com.mini.web.config;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.io.Serializable;
import java.util.*;

public final class FilterElement implements EventListener, Serializable {
    private final List<DispatcherType> dispatcherTypes = new ArrayList<>();
    private final Set<String> urlPatterns = new HashSet<>();
    private boolean matchAfter = true;
    private Filter filter;

    public FilterElement addDispatcherTypes(DispatcherType... dispatcherTypes) {
        Collections.addAll(this.dispatcherTypes, dispatcherTypes);
        return this;
    }

    public FilterElement addUrlPatterns(String... urlPatterns) {
        Collections.addAll(this.urlPatterns, urlPatterns);
        return this;
    }

    public FilterElement setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public FilterElement setMatchAfter(boolean matchAfter) {
        this.matchAfter = matchAfter;
        return this;
    }


    public FilterElement clearUrlPatterns() {
        urlPatterns.clear();
        return this;
    }

    public List<DispatcherType> getDispatcherTypes() {
        return dispatcherTypes;
    }

    public String[] getUrlPatterns() {
        return urlPatterns.toArray(new String[0]);
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean isMatchAfter() {
        return matchAfter;
    }


}
