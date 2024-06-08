package org.example.system.controller;

import org.example.system.utils.ResultMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * web层通用数据处理
 *
 * @author David
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回成功
     */
    protected ResultMap success() {
        return ResultMap.success(null, null);
    }


    /**
     * 返回成功
     *
     * @param message 消息
     */
    protected ResultMap success(String message) {
        return ResultMap.success(message, null);
    }

    /**
     * 返回成功
     *
     * @param data 数据
     * @param message 消息
     */
    protected ResultMap success(String message, Object data) {
        return ResultMap.success(message, data);
    }

    /**
     * 返回错误消息
     */
    protected ResultMap fail() {
        return ResultMap.fail(null);
    }

    /**
     * 返回错误消息
     *
     * @param message
     */
    protected ResultMap fail(String message) {
        return ResultMap.fail(message);
    }

}

