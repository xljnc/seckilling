package com.wt.seckilling.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.ManagedBean;

/**
 * @author: qiyu
 * @date: 2019/12/11 09:46
 * @description:
 */
@ManagedBean
@RequestScope
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeckillingRequestContext {

    private Object requestBody;

}
