package com.furenqiang.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.furenqiang.business.valid.AddGroup;
import com.furenqiang.business.valid.ListValue;
import com.furenqiang.business.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 品牌
 * 
 * @author Eric
 * @email www.1726894668@foxmail.com
 * @date 2021-07-22 18:02:14
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @NotNull(groups = {UpdateGroup.class})
    private Long brandId;
    /**
     * 品牌名
     */
    @NotNull(groups = {UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    private String logo;
    /**
     * 介绍
     */
    @NotEmpty(groups = {UpdateGroup.class})
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @ListValue(vals = {0,1},groups = {AddGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    private String firstLetter;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 上架状态
     */
    @TableField(exist = false)
    private Integer upStatus;

}
