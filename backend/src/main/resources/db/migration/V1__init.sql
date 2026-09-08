-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
CREATE TABLE operation_record (
 id BIGINT NOT NULL AUTO_INCREMENT,
 request_id VARCHAR(80) NOT NULL,
 operation_type VARCHAR(40) NOT NULL,
 decision VARCHAR(30) NOT NULL,
 risk_level VARCHAR(20) NOT NULL,
 summary VARCHAR(500) NOT NULL,
 created_by VARCHAR(80) NOT NULL,
 created_at TIMESTAMP(6) NOT NULL,
 PRIMARY KEY (id),
 CONSTRAINT uk_operation_request UNIQUE (request_id)
);
CREATE INDEX idx_operation_created ON operation_record(created_at);
