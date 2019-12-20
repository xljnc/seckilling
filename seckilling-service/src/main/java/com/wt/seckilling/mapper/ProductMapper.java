package com.wt.seckilling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wt.seckilling.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: qiyu
 * @date: 2019/12/19 20:57
 * @description:
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
