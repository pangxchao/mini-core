package com.mini.core.mvc.util;

import java.util.EventListener;

public interface WebSession<T> extends EventListener {
    String SESSION_KEY = "MINI_SESSION_KEY";

    T getId();
}