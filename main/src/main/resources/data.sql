USE
coupang;

/* gathering_content 데이터 삽입 */
INSERT INTO gathering_content (title, thumbnail, content, contents_id, pick_order)
VALUES ('남양주', 'gathering123124contentsPhoto', '안녕하세요ㆍ반갑습니다', '423', 2);
INSERT INTO gathering_content (title, thumbnail, content, contents_id, pick_order)
VALUES ('미국여행중', 'gathering123124contentsPhoto', '새롭게 가입하신 회원여러분', '737', 2);
INSERT INTO gathering_content (title, thumbnail, content, contents_id, pick_order)
VALUES ('외로움을함께하실분', 'gathering1231412contentsPhoto', '이성친구 만남을 소중하게 서로서로 ', '274', 2);

INSERT INTO interest (code_group, code_name, created_at, updated_at, deleted_at)
VALUES ('여행', '교양', '4321', '1234', NULL);
INSERT INTO interest (code_group, code_name, created_at, updated_at, deleted_at)
VALUES ('여행', '양주', '4321', '1234', NULL);
INSERT INTO interest (code_group, code_name, created_at, updated_at, deleted_at)
VALUES ('여행', '양구', '4321', '1234', NULL);

INSERT INTO product (info, thumbnail, sub_title, product_name, price, discount_price, option_id, company)
VALUES
    ('<div><img style="width:100%" src="https://sinor-produc', 'systemPhoto/massage_gu', '부위별 맞춤 4종 헤드 구성 마사지건', '마사지건 JSK-N3001 블랙', 199900, 30000, 35, '셰퍼'),
    ('<div><img style="width:100%" src="https://sinor-product', 'systemPhoto/massage_gu', '부위별 맞춤 4종 헤드 구성 마사지건', '마사지건 JSK-N3001 레드', 199900, 30000, 36, '셰퍼');


INSERT INTO banner (exposure_count, image_url, link_url, title)
VALUES
    (100, 'https://example.com/image1.jpg', 'https://example.com/link1', 'Banner 1'),
    (150, 'https://example.com/image2.jpg', 'https://example.com/link2', 'Banner 2'),
    (200, 'https://example.com/image3.jpg', 'https://example.com/link3', 'Banner 3');

INSERT INTO expression (category, category_name, code_name)
VALUES
    (1, 'https://example.com/link1', 'expression 1'),
    (2, 'https://example.com/link2', 'expression 2'),
    (3, 'https://example.com/link3', 'expression 3');

INSERT INTO gathering (created_at, crew_count, date_yn, distance, new_yn, origin_yn, name, thumbnail)
VALUES
    ('2023-12-01 12:30:00', 50, 1, 100, 0, 1, 'Gathering 1', 'https://example.com/thumbnail1.jpg'),
    ('2023-12-02 14:45:00', 30, 1, 150, 1, 0, 'Gathering 2', 'https://example.com/thumbnail2.jpg'),
    ('2023-12-03 10:00:00', 80, 0, 200, 1, 1, 'Gathering 3', 'https://example.com/thumbnail3.jpg');