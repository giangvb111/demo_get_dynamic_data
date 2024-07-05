package com.example.demo.constant;

public class CustomerQueryConstant {

    public static final String SEARCH_GOODS_RECEIVE_BY_PARAM = "SELECT * " +
            "FROM goods_receive_header AS grh" +
            "INNER JOIN goods_receive_detail AS grd ON grh.goods_receive_header_id = grd.goods_receive_header_id" +
            "INNER JOIN product AS p ON grd.product_id = p.product_id ";

}
