package com.furenqiang.business.web;

import com.furenqiang.business.entity.BrandEntity;
import com.furenqiang.business.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    BrandService brandService;

    @GetMapping({"/","/index.html"})
    public String indexPage(@RequestParam("brandId") Long brandId, Model model){
        BrandEntity byId = brandService.getById(brandId);
        model.addAttribute("data",byId);
        return "demo";
    }
}
