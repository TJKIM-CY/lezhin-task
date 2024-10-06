CREATE SCHEMA IF NOT EXISTS lezhindb;
SET SCHEMA lezhindb;

CREATE TABLE USERS
(
    ID         VARCHAR(100) NOT NULL COMMENT '유저 ID' PRIMARY KEY,
    PASSWORD   VARCHAR(255) NOT NULL,
    IS_ADULT   BOOLEAN DEFAULT FALSE NOT NULL COMMENT '성인 인증 여부',
    CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '생성일',
    UPDATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '수정일'
);

CREATE TABLE CONTENT
(
    ID          CHAR(36)     NOT NULL COMMENT '작품 ID' PRIMARY KEY,
    TITLE       VARCHAR(100) NOT NULL COMMENT '작품 제목',
    DESCRIPTION TEXT NULL COMMENT '작품 설명',
    PRICE       INT NULL COMMENT '작품 금액',
    IS_PAID     BOOLEAN DEFAULT FALSE NOT NULL COMMENT '유료 여부',
    IS_ADULT    BOOLEAN DEFAULT FALSE NOT NULL,
    CREATED_BY  VARCHAR(100) NULL COMMENT '생성자',
    UPDATED_BY  VARCHAR(100) NULL COMMENT '수정자',
    CREATED_AT  DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '생성일',
    UPDATED_AT  DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '수정일'
);

CREATE TABLE CONTENT_FREE_PROMOTION
(
    ID         CHAR(36) NOT NULL COMMENT '프로모션 ID' PRIMARY KEY,
    CONTENT_ID CHAR(36) NULL COMMENT '작품 ID',
    START_DATE DATETIME NOT NULL COMMENT '프로모션 시작일',
    END_DATE   DATETIME NOT NULL COMMENT '프로모션 종료일',
    CREATED_BY VARCHAR(100) NULL COMMENT '생성자',
    UPDATED_BY VARCHAR(100) NULL COMMENT '수정자',
    CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '생성일',
    UPDATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '수정일',
    CONSTRAINT CONTENT_FREE_PROMOTION_IBFK_1 FOREIGN KEY (CONTENT_ID) REFERENCES CONTENT (ID) ON DELETE SET NULL
);

CREATE INDEX CONTENT_FREE_PROMOTION_CONTENT_ID_IDX ON CONTENT_FREE_PROMOTION (CONTENT_ID);

CREATE TABLE CONTENT_VIEW_HISTORY
(
    ID         CHAR(36)     NOT NULL COMMENT '작품 조회 이력 ID' PRIMARY KEY,
    USER_ID    VARCHAR(100) NOT NULL COMMENT '유저 ID',
    CONTENT_ID CHAR(36)     NOT NULL COMMENT '작품 ID',
    VIEWED_AT  DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '조회일',
    CONSTRAINT CONTENT_VIEW_HISTORY_IBFK_1 FOREIGN KEY (USER_ID) REFERENCES USERS (ID),
    CONSTRAINT CONTENT_VIEW_HISTORY_IBFK_2 FOREIGN KEY (CONTENT_ID) REFERENCES CONTENT (ID)
);

CREATE INDEX CONTENT_VIEW_HISTORY_CONTENT_ID_IDX ON CONTENT_VIEW_HISTORY (CONTENT_ID);

CREATE INDEX CONTENT_VIEW_HISTORY_USER_ID_IDX ON CONTENT_VIEW_HISTORY (USER_ID);

CREATE TABLE CONTENT_PURCHASE
(
    ID            CHAR(36)     NOT NULL COMMENT '구매 ID' PRIMARY KEY,
    USER_ID       VARCHAR(100) NOT NULL COMMENT '유저 ID',
    CONTENT_ID    CHAR(36)     NULL COMMENT '작품 ID',
    PROMOTION_ID  CHAR(36) NULL COMMENT '프로모션 ID',
    PAYMENT_PRICE INT NULL COMMENT '결제 금액',
    PURCHASED_AT  DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '구매일',
    CONSTRAINT CONTENT_PURCHASE_IBFK_1 FOREIGN KEY (USER_ID) REFERENCES USERS (ID),
    CONSTRAINT CONTENT_PURCHASE_IBFK_2 FOREIGN KEY (CONTENT_ID) REFERENCES CONTENT (ID) ON DELETE SET NULL,
    CONSTRAINT CONTENT_PURCHASE_IBFK_3 FOREIGN KEY (PROMOTION_ID) REFERENCES CONTENT_FREE_PROMOTION (ID)
);

CREATE INDEX CONTENT_PURCHASE_CONTENT_ID_IDX ON CONTENT_PURCHASE (CONTENT_ID);

CREATE INDEX CONTENT_PURCHASE_PROMOTION_ID_IDX ON CONTENT_PURCHASE (PROMOTION_ID);

CREATE INDEX CONTENT_PURCHASE_USER_ID_IDX ON CONTENT_PURCHASE (USER_ID);

