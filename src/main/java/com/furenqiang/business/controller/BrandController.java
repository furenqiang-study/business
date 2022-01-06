package com.furenqiang.business.controller;

import com.furenqiang.business.entity.BrandEntity;
import com.furenqiang.business.service.BrandService;
import com.furenqiang.business.utils.PageUtils;
import com.furenqiang.business.utils.R;
import com.furenqiang.business.valid.AddGroup;
import com.furenqiang.business.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 品牌
 *
 * @author Eric
 * @email www.1726894668@foxmail.com
 * @date 2021-07-22 18:02:14
 */
@RestController
@RequestMapping("business/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 商品上架（将信息存ES）
     */
    @GetMapping("/testSentinel")
    //@RequiresPermissions("business:brand:list")
//    @SentinelResource(value = "",blockHandler = "")
    public R testSentinel() throws IOException {
        return R.ok();
    }

    /**
     * 商品上架（将信息存ES）
     */
    @PostMapping("/{brandId}/up")
    //@RequiresPermissions("business:brand:list")
    public R upBrand(@PathVariable Long brandId) throws IOException {
        boolean b = brandService.upBrand(brandId);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("business:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("business:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("business:brand:save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand) {
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("business:brand:update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("business:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
