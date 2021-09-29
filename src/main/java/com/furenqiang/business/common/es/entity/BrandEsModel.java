package com.furenqiang.business.common.es.entity;

import lombok.Data;

import java.util.List;

@Data
public class BrandEsModel {
    /**
     * 品牌id
     */
    private Long brandId;

    private Long catelogId;

    private String catelogName;
    /**
     * 品牌名
     */
    private String name;
    /**
     * 品牌logo地址
     */
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    private Integer showStatus;
    /**
     * 检索首字母
     */
    private String firstLetter;
    /**
     * 排序
     */
    private Integer sort;

    private List<Attrs> attrs;

    @Data
    public static class Attrs{

        private Long attrId;

        private String attrName;

        private String attrValue;
    }

}
