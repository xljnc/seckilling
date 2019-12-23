package com.wt.seckilling.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.wt.seckilling.exception.SeckillingRuntimeException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: qiyu
 * @date: 2019/12/19 13:52
 * @description:
 */
@Component
@Aspect
@Setter
@Slf4j
public class ServiceLimitAspect implements InitializingBean {

    @Value("${service.limit.rate:200.0}")
    private Double rateServiceLimit;

    @Value("${service.limit.semaphore:200}")
    private Integer semaphoreServiceLimit;

    private RateLimiter rateLimiter;

    private Semaphore semaphore;

    /**
     * rate service limit aspect
     **/
    @Pointcut("@annotation(com.wt.seckilling.aop.RateServiceLimit)")
    public void RateServiceLimitAspect() {
    }

    @Around("RateServiceLimitAspect()")
    public Object rateLimit(ProceedingJoinPoint joinPoint) {
        Boolean flag = rateLimiter.tryAcquire(1, 200, TimeUnit.MILLISECONDS);
        try {
            Object result = null;
            if (flag)
                result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            log.error("Rate service limit error", e);
            return null;
        }
    }

    /**
     * Semaphore service limit aspect
     **/
    @Pointcut("@annotation(com.wt.seckilling.aop.SemaphoreServiceLimit)")
    public void SemaphoreServiceLimitAspect() {
    }

    @Around("SemaphoreServiceLimitAspect()")
    public Object semaphoreLimit(ProceedingJoinPoint joinPoint) {
        try {
            log.info("Semaphore before available permits:{}",semaphore.availablePermits());
            Boolean flag = semaphore.tryAcquire(1, 200, TimeUnit.MILLISECONDS);
            Object result = null;
            if (flag) {
                log.info("Semaphore after available permits:{}",semaphore.availablePermits());
                result = joinPoint.proceed();
            }
            return result;
        } catch (SeckillingRuntimeException e) {
//            semaphore.release();
            throw e;
        } catch (Throwable e) {
            log.error("Semaphore service limit error", e);
            return null;
        }finally {
            semaphore.release();
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        rateLimiter = RateLimiter.create(100.0);
        semaphore = new Semaphore(semaphoreServiceLimit);
    }
}
