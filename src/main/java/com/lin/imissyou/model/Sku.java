package com.lin.imissyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lin.imissyou.util.GenericAndJson;
import com.lin.imissyou.util.ListAndJson;
import com.lin.imissyou.util.MapAndJson;
import com.lin.imissyou.util.SuperGenericAndJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
public class Sku extends BaseEntity {

    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private String code;

//    public List<Spec> getSpecs() {
//        if (this.specs == null) {
//            return Collections.emptyList();
//        }
//        return GenericAndJson.jsonToList(this.specs);
//    }
//
//    public void setSpecs(List<Spec> specs) {
//        if (specs == null) {
//            return;
//        }
//        this.specs = GenericAndJson.objectToJson(specs);
//    }

    private Long stock;
    private Integer categoryId;
    private Integer rootCategoryId;

    @Convert(converter = SuperGenericAndJson.class)
    private List<Spec> specs;

//    @Convert(converter = MapAndJson.class)
//    private Map<String, Object> test;
}
