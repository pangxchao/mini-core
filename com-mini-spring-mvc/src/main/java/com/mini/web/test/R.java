package com.mini.web.test;

import javax.inject.Inject;
import javax.inject.Named;

public final class R {
    @Inject
    @Named("KEY_PATH")
    private static String path;
    @Inject
    @Named("KEY_URL")
    private static String url;

    public static String getPath() {
        return path;
    }

    public static String getUrl() {
        return url;
    }
}
