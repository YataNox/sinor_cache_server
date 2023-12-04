CREATE
DATABASE IF NOT EXISTS coupang;
USE
coupang;

-- User 테이블
DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
    id       int unsigned NOT NULL AUTO_INCREMENT,
    email    varchar(50)  NOT NULL,
    password varchar(200) NOT NULL,
    name     varchar(50),
    created  timestamp   DEFAULT CURRENT_TIMESTAMP,
    updated  timestamp   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status   varchar(10) DEFAULT 'ACTIVE',
    CONSTRAINT USER_PK PRIMARY KEY (id)
);

-- OrderDetail 테이블
DROP TABLE IF EXISTS ORDER_DETAIL;
CREATE TABLE ORDER_DETAIL
(
    id                        int unsigned NOT NULL AUTO_INCREMENT,
    created                   timestamp   DEFAULT CURRENT_TIMESTAMP,
    updated                   timestamp   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status                    varchar(10) DEFAULT 'ACTIVE',
    `order_id`                bigint NOT NULL,
    `product_detail_id`       bigint NOT NULL,
    `order_detail_unit_price` integer NULL,
    `order_detail_quantity`   integer NULL DEFAULT 1,
    `order_detail_discount`   integer NULL,
    `order_detail_price`      integer NULL,
    `order_detail_status`     varchar(45) NULL,
    `delivery_id`             bigint NOT NULL,
    CONSTRAINT ORDER_DETAIL_PK PRIMARY KEY (id)
);