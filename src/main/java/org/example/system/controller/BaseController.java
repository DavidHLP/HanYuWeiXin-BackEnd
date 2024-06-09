package org.example.system.controller;

import org.example.system.utils.ResultMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * web层通用数据处理
 *
 * @since 1.0
 * @author David
 */
public class BaseController {

    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回成功结果
     *
     * @return 成功的 ResultMap
     */
    protected ResultMap success() {
        return ResultMap.success(null, null);
    }

    /**
     * 返回成功结果
     *
     * @param message 消息
     * @return 成功的 ResultMap
     */
    protected ResultMap success(String message) {
        return ResultMap.success(message, null);
    }

    /**
     * 返回成功结果
     *
     * @param message 消息
     * @param data 数据
     * @return 成功的 ResultMap
     */
    protected ResultMap success(String message, Object data) {
        return ResultMap.success(message, data);
    }

    /**
     * 返回错误消息
     *
     * @return 失败的 ResultMap
     */
    protected ResultMap fail() {
        return ResultMap.fail(null);
    }

    /**
     * 返回错误消息
     *
     * @param message 错误消息
     * @return 失败的 ResultMap
     */
    protected ResultMap fail(String message) {
        return ResultMap.fail(message);
    }
}
