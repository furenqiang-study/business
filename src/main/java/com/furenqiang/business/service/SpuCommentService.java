package com.furenqiang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.furenqiang.business.utils.PageUtils;
import com.furenqiang.business.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author Eric
 * @email www.1726894668@foxmail.com
 * @date 2021-07-22 18:02:14
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

