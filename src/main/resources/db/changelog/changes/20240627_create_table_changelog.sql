--liquibase formatted sql
--changeset admin:create
--product-table splitStatements:true endDelimiter:;

--- create table general_data_table_search
create table general_data_table_search
(
    id              BIGINT PRIMARY KEY IDENTITY(1,1),
    screen_id 		BIGINT ,
    column_name		VARCHAR(255) NOT NULL,
    data_type		VARCHAR(255) NOT NULL ,
    column_width	INT NOT NULL
)