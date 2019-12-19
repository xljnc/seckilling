package com.wt.seckilling.aop;

import java.lang.annotation.*;

/**
 * @author: qiyu
 * @date: 2019/12/19 13:56
 * @description: Service Limit, with this annotation function will be limit by Semaphore
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SemaphoreServiceLimit {
}
