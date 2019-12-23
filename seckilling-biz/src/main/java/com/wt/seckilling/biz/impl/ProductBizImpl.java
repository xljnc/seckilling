package com.wt.seckilling.biz.impl;

import ai.ii.common.redis.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.wt.seckilling.biz.ProductBiz;
import com.wt.seckilling.dto.ProductCreateDTO;
import com.wt.seckilling.dto.ProductUpdateDTO;
import com.wt.seckilling.entity.Product;
import com.wt.seckilling.exception.MessageAlreadyConsumedException;
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

    private String productOrdersRedisPrefix = "PRODUCT::ORDERS::";

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
        //这个用法不好，写redis应该在用户读取的时候，Demo代码是为了方便
        redisUtil.setObjectValue(redisKeyPrefix + productRedisPrefix + product.getProductId(), product);
        redisUtil.setObjectValue(redisKeyPrefix + productStockRedisPrefix + product.getProductId(), ((Double) (product.getStock() * productStockFaultTolerance)).intValue());
//        redisUtil.setObjectValue(redisKeyPrefix + productOrdersRedisPrefix + product.getProductId(), new HashSet<Long>());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductUpdateDTO updateDTO) {
        Product product = JSON.parseObject(JSON.toJSONString(updateDTO), Product.class);
        Date now = new Date();
        product.setModifyTime(now);
        productService.updateById(product);
        //这个用法不好,这里应该是删除缓存，而不是读取后写入，Demo代码是为了方便
        product = productService.getById(product.getProductId());
        redisUtil.setObjectValue(redisKeyPrefix + productRedisPrefix + product.getProductId(), product);
        redisUtil.setObjectValue(redisKeyPrefix + productStockRedisPrefix + product.getProductId(), ((Double) (product.getStock() * productStockFaultTolerance)).intValue());
        redisUtil.deleteKey(redisKeyPrefix + productOrdersRedisPrefix + product.getProductId());

    }

    @Override
    public void decreaseRedisStock(Long productId, int num, Long customerId) {
        String ordersRedisKey = redisKeyPrefix + productOrdersRedisPrefix + productId;
        boolean orderExisted = redisUtil.sIsMember(ordersRedisKey, customerId);
        if (orderExisted)
            throw new SeckillingRuntimeException(9007, String.format("用户%x重复下单商品%x", customerId, productId));
        String stockRedisKey = redisKeyPrefix + productStockRedisPrefix + productId;
        Integer stock = (Integer) redisUtil.getObjectValue(stockRedisKey);
        if (stock < num)
            throw new SeckillingRuntimeException(9002, "库存不足");
        boolean result = redisUtil.decrement(stockRedisKey, Long.valueOf(num));
        if (!result)
            throw new SeckillingRuntimeException(9006, "redis库存扣减失败");
        redisUtil.sAdd(ordersRedisKey, customerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseDatabaseStock(Long productId, int num) {
        Product product = productService.getById(productId);
        if (product == null)
            throw new SeckillingRuntimeException(9005, "商品不存在");
        if (product.getStock().compareTo(num) < 0)
            throw new MessageAlreadyConsumedException(9002, "商品库存不足");
        boolean result = productService.decreaseDatabaseStock(product, num);
        if (!result)
            throw new SeckillingRuntimeException(9006, "数据库库存扣减失败");
    }
}
