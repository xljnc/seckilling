package com.wt.seckilling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wt.seckilling.entity.Product;

/**
 * @author: qiyu
 * @date: 2019/12/19 20:56
 * @description:
 */
public interface ProductService extends IService<Product> {

    boolean decreaseDatabaseStock(Product product, int num);
}
