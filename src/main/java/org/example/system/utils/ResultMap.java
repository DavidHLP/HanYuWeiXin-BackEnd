package org.example.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResultMap 是一个泛型类，用于封装 API 请求的响应结果。
 * 它包含状态码、消息和数据。
 * @since 1.0
 * @author David
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMap<T> {

    /**
     * 状态码，表示请求的处理结果，例如200表示成功，500表示失败。
     */
    private int statusCode;

    /**
     * 消息，提供对请求处理结果的描述。
     */
    private String message;

    /**
     * 数据，泛型类型，用于存储请求返回的数据。
     */
    private T data;

    /**
     * 成功响应的静态方法，包含消息和数据。
     *
     * @param message 响应消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return ResultMap 对象
     */
    public static <T> ResultMap<T> success(String message, T data) {
        ResultMap<T> result = new ResultMap<>(200, message, data);
        return result;
    }

    /**
     * 成功响应的静态方法，只包含消息。
     *
     * @param message 响应消息
     * @param <T> 数据类型
     * @return ResultMap 对象
     */
    public static <T> ResultMap<T> success(String message) {
        ResultMap<T> result = new ResultMap<>(200, message, null);
        return result;
    }

    /**
     * 失败响应的静态方法，只包含消息。
     *
     * @param message 响应消息
     * @return ResultMap 对象
     */
    public static ResultMap fail(String message) {
        ResultMap resultMap = new ResultMap<>(500, message, null);
        return resultMap;
    }
}
