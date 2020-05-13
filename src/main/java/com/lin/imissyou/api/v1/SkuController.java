package com.lin.imissyou.api.v1;

import com.lin.imissyou.model.Sku;
import com.lin.imissyou.services.SkuService;
import com.lin.imissyou.vo.SkuSimplityVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping
    public List<SkuSimplityVO> getSkus(@RequestParam List<Long> ids) {
        List<Sku> skus = skuService.getSkuListByIds(ids);
        List<SkuSimplityVO> simplityVOS = skus.stream().map(sku -> {
            return new SkuSimplityVO(sku);
        }).collect(Collectors.toList());
        return simplityVOS;
    }

}
