INSERT INTO product_category(id, description, type, created_at, modified_at)
VALUES (1, '화폭에 옮긴 그림을 만나실 수 있습니다.', 'PAINT', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (2, '장인의 혼이 담긴 고귀한 도자기를 보실 수 있습니다.', 'CERAMIC', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (3, '기타 작품들입니다.', 'ETC', TIMESTAMP(NOW()),TIMESTAMP(NOW()));

INSERT INTO cart(id, created_at, modified_at)
VALUES (1, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (2, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (3, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (4, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (5, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (6, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (7, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (8, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (9, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (10, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (11, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (12, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (13, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (14, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (15, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (16, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (17, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (18, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (19, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (20, TIMESTAMP(NOW()),TIMESTAMP(NOW())),
       (21, TIMESTAMP(NOW()),TIMESTAMP(NOW()));

INSERT INTO address(id, address, address_detail, userId) VALUES


INSERT INTO users(user_id, cart_id ,username,password,nickname,email,introduction,phone_number,address,address_detail,is_email_yn,is_deleted,user_role,user_status,created_at,modified_at)
VALUES
    (UNHEX(REPLACE(UUID(), '-', '')), 1, 'user1', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser1','user1@gmail.com','유저 1호입니다.','010-1234-5678','서울특별시 광진구 가로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 2, 'user2', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser2','user2@gmail.com','유저 2호입니다.','010-1234-5678','서울특별시 광진구 나로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 3, 'user3', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser3','user3@gmail.com','유저 3호입니다.','010-1234-5678','서울특별시 광진구 다로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 4, 'user4', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser4','user4@gmail.com','유저 4호입니다.','010-1234-5678','서울특별시 광진구 라로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 5, 'user5', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser5','user5@gmail.com','유저 5호입니다.','010-1234-5678','서울특별시 광진구 마로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 6, 'user6', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser6','user6@gmail.com','유저 6호입니다.','010-1234-5678','서울특별시 광진구 바로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 7, 'user7', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser7','user7@gmail.com','유저 7호입니다.','010-1234-5678','서울특별시 광진구 사로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 8, 'user8', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser8','user8@gmail.com','유저 8호입니다.','010-1234-5678','서울특별시 광진구 아로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 9, 'user9', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser9','user9@gmail.com','유저 9호입니다.','010-1234-5678','서울특별시 광진구 자로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 10, 'user10', '$2a$10$vh4UtJw2UNaMSFyvCUSFRuIIb9uf1gPq0c2ATw1npdMao7bBs0hiu','nuser10','user10@gmail.com','유저 10호입니다.','010-1234-5678','서울특별시 광진구 카로 1길','101호',TRUE,FALSE,'USER','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 11, 'artist1', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist1','artist1@gmail.com','작가 1호입니다.','010-1234-5678','서울특별시 관악구 나로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 12, 'artist2', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist2','artist2@gmail.com','작가 2호입니다.','010-1234-5678','서울특별시 관악구 다로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 13, 'artist3', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist3','artist3@gmail.com','작가 3호입니다.','010-1234-5678','서울특별시 관악구 라로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 14, 'artist4', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist4','artist4@gmail.com','작가 4호입니다.','010-1234-5678','서울특별시 관악구 마로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 15, 'artist5', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist5','artist5@gmail.com','작가 5호입니다.','010-1234-5678','서울특별시 관악구 바로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 16, 'artist6', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist6','artist6@gmail.com','작가 6호입니다.','010-1234-5678','서울특별시 관악구 사로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 17, 'artist7', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist7','artist7@gmail.com','작가 7호입니다.','010-1234-5678','서울특별시 관악구 아로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 18, 'artist8', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist8','artist8@gmail.com','작가 8호입니다.','010-1234-5678','서울특별시 관악구 자로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 19, 'artist9', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist9','artist9@gmail.com','작가 9호입니다.','010-1234-5678','서울특별시 관악구 차로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 20, 'artist10', '$2a$10$twSTwrXsa4ux5Or38.tzdOqtQ1OjwsoMqSC2pxILsfev5t3k5hI3G','nartist10','artist10@gmail.com','작가 10호입니다.','010-1234-5678','서울특별시 관악구 카로 1길','101호',TRUE,FALSE,'ARTIST','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (UNHEX(REPLACE(UUID(), '-', '')), 21, 'admin','$2a$10$krkZmlf2jCf9VqR42pwbQ.6z1q/nJt.0G2CRP3WsxUOX8JnzNWqQu','nadmin','admin1@gmail.com','관리자 계정입니다.','010-1234-5678','서울특별시 관악구 가로 1길','101호',TRUE,FALSE,'ADMIN','ACTIVE',TIMESTAMP(NOW()),TIMESTAMP(NOW()));


INSERT INTO notice(id, title, content, created_at, modified_at)
VALUES
    (1, '상품 구매 시 주의사항', '공지사항 내용 1', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (2, '크리스마스 배송 지연 안내', '공지사항 내용 2', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (3, '작가 문의 양식 안내', '공지사항 내용 3', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (4, '사이트 내 관리자 문의 방법', '공지사항 내용 4', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (5, '주문 제작(커미션) 요청 방법', '공지사항 내용 5', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (6, '개인 결제창 안내', '공지사항 내용 6', TIMESTAMP(NOW()),TIMESTAMP(NOW())),
    (7, '환불액 미수령자 공지', '공지사항 내용 7', TIMESTAMP(NOW()),TIMESTAMP(NOW()));