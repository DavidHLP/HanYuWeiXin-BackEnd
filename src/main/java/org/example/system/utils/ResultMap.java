package org.example.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMap<T> {
    private int statusCode;
    private String message;
    private T data;

    public static <T> ResultMap<T> success(String message, T data) {
        ResultMap<T> result = new ResultMap(200, message, data);
        return result;
    }

    public static <T> ResultMap<T> success(String message) {
        ResultMap<T> result = new ResultMap(200, message, null);
        return result;
    }

    public static ResultMap fail(String message) {
        ResultMap resultMap = new ResultMap(500, message, null);
        return resultMap;
    }


}
