USE coupang;

-- 예제 데이터 추가
INSERT INTO USER(email, password, name) VALUES('email1@naver.com', '1234', 'name1');
INSERT INTO USER(email, password, name) VALUES('email2@naver.com', '1234', 'name2');
INSERT INTO USER(email, password, name) VALUES('email3@naver.com', '1234', 'name3');

INSERT INTO ORDER_DETAIL (`order_id`, `product_detail_id`, `order_detail_unit_price`, `order_detail_quantity`, `order_detail_discount`, `order_detail_price`, `order_detail_status`, `delivery_id`) VALUES ('1', '1', '12000', '1', '2000', '10000', 'delivering', '1');
INSERT INTO ORDER_DETAIL (`order_id`, `product_detail_id`, `order_detail_unit_price`, `order_detail_quantity`, `order_detail_discount`, `order_detail_price`, `order_detail_status`, `delivery_id`) VALUES ('1', '2', '12000', '1', '2000', '10000', 'delivering', '12341234');

