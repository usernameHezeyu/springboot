package com.example.spring.security.spring.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sale_daily
 * @author 
 */
@Data
public class SaleDaily implements Serializable {
    private Integer id;

    private String brand;

    private String country;

    private String sku;

    private String asin;

    private String pname;

    private Double price;

    private Integer units;

    /**
     * 日期
     */
    private Date sdate;

    /**
     * 产品开发名称
     */
    private String uname;

    /**
     * 产品分类
     */
    private String pcat;

    private static final long serialVersionUID = 1L;
}