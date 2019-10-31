package com.mini.web.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * StringModel.java
 * @author xchao
 */
public final class StringModel extends IModel<StringModel> implements Serializable {
    private static final long serialVersionUID = -1731063292578685253L;
    private final StringBuilder builder = new StringBuilder();
    private static final String TYPE = "text/plain";

    public StringModel() {
        super(TYPE);
    }

    @Override
    protected StringModel model() {
        return this;
    }

    /**
     * 获取所有数据
     * @return 所有数据
     */
    public StringBuilder getBuilder() {
        return builder;
    }

    public StringModel clear() {
        builder.setLength(0);
        return this;
    }

    public StringModel append(Object text) {
        builder.append(text);
        return this;
    }

    public StringModel append(String text) {
        builder.append(text);
        return this;
    }

    public StringModel append(StringBuffer buffer) {
        builder.append(buffer);
        return this;
    }

    public StringModel append(CharSequence text) {
        builder.append(text);
        return this;
    }

    public StringModel append(CharSequence text, int start, int end) {
        builder.append(text, start, end);
        return this;
    }

    public StringModel append(char[] text) {
        builder.append(text);
        return this;
    }

    public StringModel append(char[] text, int offset, int len) {
        builder.append(text, offset, len);
        return this;
    }

    public StringModel append(boolean bool) {
        builder.append(bool);
        return this;
    }

    public StringModel append(char ch) {
        builder.append(ch);
        return this;
    }

    public StringModel append(int i) {
        builder.append(i);
        return this;
    }

    public StringModel append(long lng) {
        builder.append(lng);
        return this;
    }

    public StringModel append(float f) {
        builder.append(f);
        return this;
    }

    public StringModel append(double d) {
        builder.append(d);
        return this;
    }

    public StringModel delete(int start, int end) {
        builder.delete(start, end);
        return this;
    }

    public StringModel deleteCharAt(int index) {
        builder.deleteCharAt(index);
        return this;
    }


    public StringModel replace(int start, int end, String text) {
        builder.replace(start, end, text);
        return this;
    }

    @Override
    protected void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(builder.toString());
            writer.flush();
        }
    }
}
