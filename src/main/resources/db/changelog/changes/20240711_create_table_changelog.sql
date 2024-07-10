--liquibase formatted sql
--changeset admin:create
--product-table splitStatements:true endDelimiter:;

-- --- create table general_system_master
create table general_system_master
(
    id                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id                           INT NOT NULL,
    screen_code                         VARCHAR(255) NOT NULL,
    reference_screen_id                 INT ,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0
)

-- --- create table general_table_setting
create table general_table_setting
(
    id                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id                           INT NOT NULL,
    table_name                          VARCHAR(255) NOT NULL,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0
)

-- --- create table menu
create table menu
(
    id                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id                           INT NOT NULL,
    screen_code                         VARCHAR(255) NOT NULL,
    url                                 VARCHAR(255) NOT NULL,
    function_name                       VARCHAR(255) NOT NULL,
    created_at                          DATETIME2 NOT NULL,
    updated_at                          DATETIME2,
    deleted_flg			                TINYINT DEFAULT 0
)