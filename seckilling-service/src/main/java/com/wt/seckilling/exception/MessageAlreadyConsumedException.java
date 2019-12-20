package com.wt.seckilling.exception;

import lombok.Data;

/**
 * @author: qiyu
 * @date: 2019/12/10 11:38
 * @description: checked异常
 */
@Data
public class MessageAlreadyConsumedException extends RuntimeException {
    private Integer code;

    public MessageAlreadyConsumedException() {
        super();
    }

    public MessageAlreadyConsumedException(String message) {
        super(message);
    }

    public MessageAlreadyConsumedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageAlreadyConsumedException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public MessageAlreadyConsumedException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
