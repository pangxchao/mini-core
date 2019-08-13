package com.mini.web.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;

public final class StringModel extends IModel<StringModel> implements Serializable {
    private static final long serialVersionUID = -1731063292578685253L;
    private final StringBuilder builder = new StringBuilder();
    private static final String TYPE = "text/plain";

    public StringModel() {
        super(TYPE);
    }

    @Override
    protected final StringModel model() {
        return this;
    }

    public final StringModel append(Object text) {
        builder.append(text);
        return this;
    }

    public final StringModel append(String text) {
        builder.append(text);
        return this;
    }

    public final StringModel append(StringBuffer buffer) {
        builder.append(buffer);
        return this;
    }

    public final StringModel append(CharSequence text) {
        builder.append(text);
        return this;
    }

    public final StringModel append(CharSequence text, int start, int end) {
        builder.append(text, start, end);
        return this;
    }

    public final StringModel append(char[] text) {
        builder.append(text);
        return this;
    }

    public final StringModel append(char[] text, int offset, int len) {
        builder.append(text, offset, len);
        return this;
    }

    public final StringModel append(boolean bool) {
        builder.append(bool);
        return this;
    }

    public final StringModel append(char ch) {
        builder.append(ch);
        return this;
    }

    public final StringModel append(int i) {
        builder.append(i);
        return this;
    }

    public final StringModel append(long lng) {
        builder.append(lng);
        return this;
    }

    public final StringModel append(float f) {
        builder.append(f);
        return this;
    }

    public final StringModel append(double d) {
        builder.append(d);
        return this;
    }

    public final StringModel delete(int start, int end) {
        builder.delete(start, end);
        return this;
    }

    public final StringModel deleteCharAt(int index) {
        builder.deleteCharAt(index);
        return this;
    }

    public final StringModel replace(int start, int end, String text) {
        builder.replace(start, end, text);
        return this;
    }

    @Override
    protected final void sendError(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        try (PrintWriter writer = response.getWriter()) {
            response.setStatus(getStatus());
            writer.write(getMessage());
            writer.flush();
        }
    }

    @Override
    protected final void submit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(builder.toString());
            writer.flush();
        }
    }


}
