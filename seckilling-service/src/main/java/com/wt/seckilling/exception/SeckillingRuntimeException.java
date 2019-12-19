package com.wt.seckilling.exception;

import lombok.Data;

/**
 * @author: qiyu
 * @date: 2019/12/10 11:38
 * @description: checked异常
 */
@Data
public class SeckillingRuntimeException extends RuntimeException {
    private Integer code;

    public SeckillingRuntimeException() {
        super();
    }

    public SeckillingRuntimeException(String message) {
        super(message);
    }

    public SeckillingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillingRuntimeException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public SeckillingRuntimeException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
