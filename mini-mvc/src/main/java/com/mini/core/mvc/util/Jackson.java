package com.mini.core.mvc.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.*;
import static com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance;

public final class Jackson {
    private static final JsonMapper jsonMapper;

    static {
        final JsonMapper.Builder jsonMapperBuilder = JsonMapper.builder();
        // 否允许JSON字符串包含未转义的控制字符,值小于32的ASCII字符。
        jsonMapperBuilder.configure(ALLOW_UNESCAPED_CONTROL_CHARS, true);
        // 枚举的toString结果作为Json序列化的结果
        jsonMapperBuilder.configure(WRITE_ENUMS_USING_TO_STRING, true);
        // 有未知属性 要不要抛异常
        jsonMapperBuilder.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 日期类型输出为时间戳格式
        jsonMapperBuilder.configure(WRITE_DATES_AS_TIMESTAMPS, true);
        // 属性为Null的不进行序列化，只对pojo起作用，对map和list不起作用
        jsonMapperBuilder.serializationInclusion(Include.NON_NULL);
        // json是否允许属性名为单引号 ，默认是false
        jsonMapperBuilder.configure(ALLOW_SINGLE_QUOTES, true);
        // 对Json进行缩进等格式化操作
        jsonMapperBuilder.configure(INDENT_OUTPUT, true);
        // 生成 JsonMapper对象,将Long转换成String类型
        jsonMapper = jsonMapperBuilder.build();
        jsonMapper.registerModule(new SimpleModule() {{
            addSerializer(Long.class, instance);
            addSerializer(Long.TYPE, instance);
        }});
    }

    /**
     * 获取类型工厂
     *
     * @return 类型工厂
     */
    public static TypeFactory getTypeFactory() {
        return jsonMapper.getTypeFactory();
    }

    /**
     * 获取JsonMapper对象
     *
     * @return JsonMapper对象
     */
    public static JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    /**
     * 解析JSON内容
     *
     * @param text JSON内容
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(String text) {
        return jsonMapper.readTree(text);
    }

    /**
     * 解析JSON内容
     *
     * @param reader JSON内容
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(Reader reader) {
        return jsonMapper.readTree(reader);
    }

    /**
     * 解析JSON内容
     *
     * @param file JSON内容
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(File file) {
        return jsonMapper.readTree(file);
    }

    /**
     * 解析JSON内容
     *
     * @param source JSON内容
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(URL source) {
        return jsonMapper.readTree(source);
    }

    /**
     * 解析JSON内容
     *
     * @param bytes  JSON内容
     * @param offset 起始位置
     * @param length 读取长度
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(byte[] bytes, int offset, int length) {
        return jsonMapper.readTree(bytes, offset, length);
    }

    /**
     * 解析JSON内容
     *
     * @param bytes JSON内容
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(byte[] bytes) {
        return jsonMapper.readTree(bytes);
    }

    /**
     * 解析JSON内容
     *
     * @param input JSON内容
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(InputStream input) {
        return jsonMapper.readTree(input);
    }

    /**
     * 解析JSON内容
     *
     * @param parser JSON内容
     * @return 解析结果
     */
    @SneakyThrows
    public static JsonNode parse(JsonParser parser) {
        return jsonMapper.readTree(parser);
    }

    /**
     * 解析JSON内容
     *
     * @param text JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(String text, Class<T> type) {
        return jsonMapper.readValue(text, type);
    }

    /**
     * 解析JSON内容
     *
     * @param text JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(String text, JavaType type) {
        return jsonMapper.readValue(text, type);
    }

    /**
     * 解析JSON内容
     *
     * @param reader JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(Reader reader, Class<T> type) {
        return jsonMapper.readValue(reader, type);
    }

    /**
     * 解析JSON内容
     *
     * @param reader JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(Reader reader, JavaType type) {
        return jsonMapper.readValue(reader, type);
    }

    /**
     * 解析JSON内容
     *
     * @param file JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(File file, Class<T> type) {
        return jsonMapper.readValue(file, type);
    }

    /**
     * 解析JSON内容
     *
     * @param file JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(File file, JavaType type) {
        return jsonMapper.readValue(file, type);
    }

    /**
     * 解析JSON内容
     *
     * @param source JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(URL source, Class<T> type) {
        return jsonMapper.readValue(source, type);
    }

    /**
     * 解析JSON内容
     *
     * @param source JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(URL source, JavaType type) {
        return jsonMapper.readValue(source, type);
    }

    /**
     * 解析JSON内容
     *
     * @param bytes  JSON内容
     * @param offset 起始位置
     * @param length 读取长度
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(byte[] bytes, int offset, int length, Class<T> type) {
        return jsonMapper.readValue(bytes, offset, length, type);
    }

    /**
     * 解析JSON内容
     *
     * @param bytes  JSON内容
     * @param offset 起始位置
     * @param length 读取长度
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(byte[] bytes, int offset, int length, JavaType type) {
        return jsonMapper.readValue(bytes, offset, length, type);
    }

    /**
     * 解析JSON内容
     *
     * @param bytes JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(byte[] bytes, Class<T> type) {
        return jsonMapper.readValue(bytes, type);
    }

    /**
     * 解析JSON内容
     *
     * @param bytes JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(byte[] bytes, JavaType type) {
        return jsonMapper.readValue(bytes, type);
    }

    /**
     * 解析JSON内容
     *
     * @param input JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(InputStream input, Class<T> type) {
        return jsonMapper.readValue(input, type);
    }

    /**
     * 解析JSON内容
     *
     * @param input JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(InputStream input, JavaType type) {
        return jsonMapper.readValue(input, type);
    }

    /**
     * 解析JSON内容
     *
     * @param parser JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(JsonParser parser, Class<T> type) {
        return jsonMapper.readValue(parser, type);
    }

    /**
     * 解析JSON内容
     *
     * @param parser JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> T parseObject(JsonParser parser, JavaType type) {
        return jsonMapper.readValue(parser, type);
    }

    /**
     * 解析JSON内容
     *
     * @param text JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(String text, Class<T> type) {
        return Jackson.jsonMapper.readValue(text, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param text JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(String text, JavaType type) {
        return Jackson.jsonMapper.readValue(text, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param reader JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(Reader reader, Class<T> type) {
        return Jackson.jsonMapper.readValue(reader, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param reader JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(Reader reader, JavaType type) {
        return Jackson.jsonMapper.readValue(reader, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param file JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(File file, Class<T> type) {
        return Jackson.jsonMapper.readValue(file, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param file JSON内容
     * @param type 结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(File file, JavaType type) {
        return Jackson.jsonMapper.readValue(file, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param source JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(URL source, Class<T> type) {
        return Jackson.jsonMapper.readValue(source, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param source JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(URL source, JavaType type) {
        return Jackson.jsonMapper.readValue(source, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param bytes  JSON内容
     * @param offset 起始位置
     * @param length 读取长度
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(byte[] bytes, int offset, int length, Class<T> type) {
        return Jackson.jsonMapper.readValue(bytes, offset, length, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param bytes  JSON内容
     * @param offset 起始位置
     * @param length 读取长度
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(byte[] bytes, int offset, int length, JavaType type) {
        return Jackson.jsonMapper.readValue(bytes, offset, length, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param bytes JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(byte[] bytes, Class<T> type) {
        return Jackson.jsonMapper.readValue(bytes, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param bytes JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(byte[] bytes, JavaType type) {
        return Jackson.jsonMapper.readValue(bytes, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param input JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(InputStream input, Class<T> type) {
        return Jackson.jsonMapper.readValue(input, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param input JSON内容
     * @param type  结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(InputStream input, JavaType type) {
        return Jackson.jsonMapper.readValue(input, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param parser JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(JsonParser parser, Class<T> type) {
        return Jackson.jsonMapper.readValue(parser, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 解析JSON内容
     *
     * @param parser JSON内容
     * @param type   结果类型
     * @return 解析结果
     */
    @SneakyThrows
    public static <T> List<T> parseArray(JsonParser parser, JavaType type) {
        return Jackson.jsonMapper.readValue(parser, getTypeFactory().constructCollectionType(List.class, type));
    }

    /**
     * 将JSON对象转换成JSON字符串
     *
     * @param object JSON对象
     * @return JSON字符串
     */
    @SneakyThrows
    public static String toJSONString(Object object) {
        return jsonMapper.writeValueAsString(object);
    }
}
