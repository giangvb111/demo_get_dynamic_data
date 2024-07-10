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

-- --- create table setting_data
create table setting_data
(
    id              BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id 		BIGINT ,
    column_name		VARCHAR(255) NOT NULL,
    table_name		VARCHAR(255) NOT NULL,
    data_type		VARCHAR(255) NOT NULL ,
    column_width	INT NOT NULL ,
    status          TINYINT DEFAULT 1 ,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0
)

-- --- create table menu
create table menu
(
    id                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id                           INT UNIQUE NOT NULL,
    screen_code                         VARCHAR(255) NOT NULL,
    url                                 VARCHAR(255) NOT NULL,
    function_name                       VARCHAR(255) NOT NULL,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0
)

-- --- create table general_system_master
create table general_system_master
(
    id                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id                           INT NOT NULL,
    screen_code                         VARCHAR(255) NOT NULL,
    reference_screen_id                 INT ,
    reference_screen_name               VARCHAR(255) NOT NULL,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0 ,
    CONSTRAINT fk_menu_general_system_master
        FOREIGN KEY(screen_id)
            REFERENCES menu(screen_id) ,
)

-- --- create table general_table_setting
create table general_table_setting
(
    id                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id                           INT NOT NULL,
    table_name                          VARCHAR(255) NOT NULL,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0 ,
    CONSTRAINT fk_menu_general_table_setting
        FOREIGN KEY(screen_id)
            REFERENCES menu(screen_id)
)

