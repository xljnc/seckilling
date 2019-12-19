package com.wt.seckilling.exception;

import lombok.Data;

/**
 * @author: qiyu
 * @date: 2019/12/10 11:38
 * @description: checked异常
 */
@Data
public class SeckillingException extends Exception {
    private Integer code;

    public SeckillingException() {
        super();
    }

    public SeckillingException(String message) {
        super(message);
    }

    public SeckillingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillingException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public SeckillingException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
