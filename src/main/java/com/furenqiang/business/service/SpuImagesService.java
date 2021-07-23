package com.furenqiang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.furenqiang.business.utils.PageUtils;
import com.furenqiang.business.entity.SpuImagesEntity;

import java.util.Map;

/**
 * spu图片
 *
 * @author Eric
 * @email www.1726894668@foxmail.com
 * @date 2021-07-22 18:02:14
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

