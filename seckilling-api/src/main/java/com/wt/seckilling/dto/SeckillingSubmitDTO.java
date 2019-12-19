package com.wt.seckilling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: qiyu
 * @date: 2019/12/19 16:55
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeckillingSubmitDTO implements Serializable {

    private static final long serialVersionUID = -8993867565756545723L;

    private String orderDetail;
}
