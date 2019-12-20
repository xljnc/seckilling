package com.wt.seckilling.biz.impl;

import ai.ii.common.redis.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.wt.seckilling.biz.ProductBiz;
import com.wt.seckilling.dto.ProductCreateDTO;
import com.wt.seckilling.dto.ProductUpdateDTO;
import com.wt.seckilling.entity.Product;
import com.wt.seckilling.exception.SeckillingRuntimeException;
import com.wt.seckilling.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: qiyu
 * @date: 2019/12/19 20:55
 * @description:
 */
@Service
public class ProductBizImpl implements ProductBiz {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisUtil redisUtil;

    private String redisKeyPrefix = "SECKILLING::";

    private String productRedisPrefix = "PRODUCT::";

    private String productStockRedisPrefix = "PRODUCT::STOCK::";

    @Value("${product.stock.fault-tolerance:1.2}")
    private Double productStockFaultTolerance;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProduct(ProductCreateDTO createDTO) {
        Product product = JSON.parseObject(JSON.toJSONString(createDTO), Product.class);
        Date now = new Date();
        product.setCreateTime(now);
        product.setModifyTime(now);
        productService.save(product);
        product = productService.getById(product.getProductId());
        redisUtil.setObjectValue(redisKeyPrefix + productRedisPrefix + product.getProductId(), product);
        redisUtil.setObjectValue(redisKeyPrefix + productStockRedisPrefix + product.getProductId(), Integer.valueOf(String.valueOf(productStockFaultTolerance * productStockFaultTolerance)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductUpdateDTO updateDTO) {
        Product product = JSON.parseObject(JSON.toJSONString(updateDTO), Product.class);
        Date now = new Date();
        product.setModifyTime(now);
        productService.updateById(product);
        product = productService.getById(product.getProductId());
        redisUtil.setObjectValue(redisKeyPrefix + productRedisPrefix + product.getProductId(), product);
        redisUtil.setObjectValue(redisKeyPrefix + productStockRedisPrefix + product.getProductId(), Integer.valueOf(String.valueOf(productStockFaultTolerance * productStockFaultTolerance)));
    }

    @Override
    public void decreaseRedisStock(Long productId, int num) {
        String stockRedisKey = redisKeyPrefix + productStockRedisPrefix + productId;
        Integer stock = (Integer) redisUtil.getObjectValue(stockRedisKey);
        if (stock < num)
            throw new SeckillingRuntimeException(9002, "库存不足");


    }
}
