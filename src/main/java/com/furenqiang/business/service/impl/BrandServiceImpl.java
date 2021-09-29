package com.furenqiang.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.furenqiang.business.common.es.entity.BrandEsModel;
import com.furenqiang.business.common.es.serviceImpl.EsServiceImpl;
import com.furenqiang.business.dao.BrandDao;
import com.furenqiang.business.entity.AttrEntity;
import com.furenqiang.business.entity.BrandEntity;
import com.furenqiang.business.entity.CategoryBrandRelationEntity;
import com.furenqiang.business.service.AttrService;
import com.furenqiang.business.service.BrandService;
import com.furenqiang.business.service.CategoryBrandRelationService;
import com.furenqiang.business.utils.PageUtils;
import com.furenqiang.business.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    AttrService attrService;

    @Autowired
    EsServiceImpl esService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

//    @Override
//    public PageUtils queryPage(Map<String, Object> params){
//        String brandPage = stringRedisTemplate.opsForValue().get("brandPage");
//        if(StringUtils.hasLength(brandPage)){
//            return JSONObject.parseObject(brandPage,PageUtils.class);
//        }
//        PageUtils pageUtils = queryPageByDB(params);
//        stringRedisTemplate.opsForValue().set("brandPage",JSONObject.toJSONString(pageUtils));
//        return pageUtils;
//    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>().like("name",params.get("key"))
        );
        page.convert((item)->{
            Long brandId = item.getBrandId();
            CategoryBrandRelationEntity cbre = categoryBrandRelationService.getOne(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",
                    brandId).last("LIMIT 1"));
            item.setUpStatus(cbre==null?2:cbre.getUpStatus());
            return item;
        });
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public boolean upBrand(Long brandId) throws IOException {
        List<CategoryBrandRelationEntity> categoryBrandRelationEntities =
                categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId).eq("up_status",0));
        BrandEntity brandEntity = this.getById(brandId);
        List<BrandEsModel> brandEsModels = categoryBrandRelationEntities.stream().map((categoryBrandRelation) -> {
            BrandEsModel brandEsModel = new BrandEsModel();
            BeanUtils.copyProperties(brandEntity,brandEsModel);
            brandEsModel.setCatelogId(categoryBrandRelation.getCatelogId());
            brandEsModel.setCatelogName(categoryBrandRelation.getCatelogName());
            List<AttrEntity> attrEntitys = attrService.list(new QueryWrapper<AttrEntity>().eq("catelog_id", categoryBrandRelation.getCatelogId()));
            List<BrandEsModel.Attrs> attrs=new ArrayList<>();
            for (AttrEntity attrEntity : attrEntitys) {
                BrandEsModel.Attrs attr=new BrandEsModel.Attrs();
                attr.setAttrId(attrEntity.getAttrId());
                attr.setAttrName(attrEntity.getAttrName());
                attr.setAttrValue(attrEntity.getValueSelect());
                attrs.add(attr);
            }
            brandEsModel.setAttrs(attrs);
            //修改上架状态
            CategoryBrandRelationEntity categoryBrandRelationEntity=new CategoryBrandRelationEntity();
            categoryBrandRelationEntity.setUpStatus(1);
            categoryBrandRelationService.update(categoryBrandRelationEntity,new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id",
                    categoryBrandRelation.getCatelogId()).eq("brand_id",brandId));
            return brandEsModel;
        }).collect(Collectors.toList());
        List<String> es = esService.bulkAddBrandEsModel(JSONArray.toJSONString(brandEsModels));
        return es.size()>0;
    }

}