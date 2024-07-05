--liquibase formatted sql
--changeset admin:create
--product-table splitStatements:true endDelimiter:;

------ create table product
create table product
(
    product_id			 BIGINT PRIMARY KEY IDENTITY(1,1),
    product_name         VARCHAR(20) NOT NULL,
    created_at           DATETIME2 NOT NULL,
    updated_at           DATETIME2,
    deleted_flg			 TINYINT DEFAULT 0
);

--- create table warehouse
create table warehouse
(
    warehouse_id              BIGINT PRIMARY KEY IDENTITY(1,1),
    warehouse_name		      VARCHAR(255) NOT NULL ,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0
)

----- create table goods_receive_header
create table goods_receive_header
(
    goods_receive_header_id			    BIGINT PRIMARY KEY IDENTITY(1,1),
    goods_receive_header_no			    VARCHAR(20) NOT NULL,
    created_at                           DATETIME2 NOT NULL,
    updated_at                           DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0,
);

----- create table goods_receive_detail
create table goods_receive_detail
(
    goods_receive_detail_id			    BIGINT PRIMARY KEY IDENTITY(1,1),
    goods_receive_detail_no			    VARCHAR(20) NOT NULL,
    goods_receive_header_id			    BIGINT NOT NULL,
    product_id			                BIGINT NOT NULL,
    warehouse_id			            BIGINT NOT NULL,
    quantity			                DECIMAL(20,5),
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0,
    CONSTRAINT fk_product
        FOREIGN KEY(product_id)
            REFERENCES product(product_id) ,
    CONSTRAINT fk_warehouse
        FOREIGN KEY(warehouse_id)
            REFERENCES warehouse(warehouse_id) ,
    CONSTRAINT fk_goods_receive_header
        FOREIGN KEY(goods_receive_header_id)
            REFERENCES goods_receive_header(goods_receive_header_id)
);

--- create table general_data_table_search
create table general_data_table_search
(
    id              BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id 		BIGINT ,
    column_name		VARCHAR(255) NOT NULL,
    data_type		VARCHAR(255) NOT NULL ,
    column_width	INT NOT NULL
)