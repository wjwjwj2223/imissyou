package com.lin.imissyou.vo;

import com.lin.imissyou.model.Sku;
import com.lin.imissyou.model.Spec;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangjie
 */
@Getter
@Setter
@NoArgsConstructor
public class SkuSimplityVO {

    public SkuSimplityVO(Sku sku) {
        this.id = sku.getId();
        this.category_id = sku.getCategoryId();
        this.root_category_id = sku.getRootCategoryId();
        this.spu_id = sku.getSpuId();
        this.price = sku.getPrice();
        this.discount_price = sku.getDiscountPrice();
        this.online = sku.getOnline();
        this.img = sku.getImg();
        this.specs = sku.getSpecs();
        this.code = sku.getCode();
        this.stock = sku.getStock();
        this.is_test = true;
    }

    private Long id;
    private Long category_id;
    private Long root_category_id;
    private Long spu_id;
    private BigDecimal price;
    private BigDecimal discount_price;
    private Boolean online;
    private String img;
    private List<Spec> specs;
    private String code;
    private Integer stock;
    private Boolean is_test;

}
