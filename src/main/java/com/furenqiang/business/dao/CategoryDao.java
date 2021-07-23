package com.furenqiang.business.dao;

import com.furenqiang.business.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Eric
 * @email www.1726894668@foxmail.com
 * @date 2021-07-22 18:02:14
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
