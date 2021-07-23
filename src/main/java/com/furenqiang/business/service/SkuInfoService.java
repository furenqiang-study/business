package com.furenqiang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.furenqiang.business.utils.PageUtils;
import com.furenqiang.business.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author Eric
 * @email www.1726894668@foxmail.com
 * @date 2021-07-22 18:02:14
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

