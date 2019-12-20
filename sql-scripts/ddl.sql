##订单表
create table husky.orders
(
  order_id     bigint unsigned NOT NULL AUTO_INCREMENT primary key,
  order_code   varchar(64)     not null unique comment '订单号',
  product_id   bigint          not null comment '商品ID',
  customer_id  bigint          not null comment '用户ID',
  order_detail varchar(128)    not null comment '订单详情',
  create_time  timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  modify_time  timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='订单表';


##商品表
create table husky.product
(
  product_id  bigint unsigned  NOT NULL AUTO_INCREMENT primary key,
  stock       integer unsigned not null comment '商品库存',
  create_time timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  modify_time timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  version     bigint           not null default 0 comment '乐观锁版本'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='商品表';
