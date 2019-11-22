package com.mini.web.servlet;

import com.mini.web.annotation.Action;

import java.io.Serializable;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public final class RequestMappingMap<T> implements Serializable, EventListener {
    private Map<String, RequestMappingMap> mapping = new ConcurrentHashMap<>();
    private Set<Action.Method> supportMethodSet = new CopyOnWriteArraySet<>();
    private T value;



    //public RequestMappingMap<T> put(String uri, T value) {
    //
    //}
    //
    //public T get(String uri) {
    //
    //}
    //
    //public boolean containsKey(String uri) {
    //
    //}
}
