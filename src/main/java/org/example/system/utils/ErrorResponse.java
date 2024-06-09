package org.example.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorResponse 是一个用于封装错误信息的数据类。
 * 它包含描述错误的消息和附加的详细信息。
 * @since 1.0
 * @author David
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * 错误消息，提供对错误的简要描述。
     */
    private String message;

    /**
     * 有关错误的详细信息。
     */
    private String details;
}
