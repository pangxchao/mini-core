package com.mini.plugin.util;

import org.jetbrains.annotations.NotNull;

import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public final class BuilderWriter {
    private final StringBuffer buffer = new StringBuffer();
    private String packageName;
    private String fileName;

    @NotNull
    public final String getPackageName() {
        return defaultIfBlank(packageName, "");
    }

    public final void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @NotNull
    public final String getFileName() {
        return defaultIfBlank(fileName, "");
    }

    public final void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public final void print(String x) {
        this.buffer.append(x);
    }

    public final void println(String x) {
        this.print(x);
        print("\n");
    }

    public final String getBuffer() {
        return buffer.toString();
    }
}