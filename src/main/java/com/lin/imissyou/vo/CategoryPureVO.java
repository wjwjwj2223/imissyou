package com.lin.imissyou.vo;

import com.lin.imissyou.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
public class CategoryPureVO {
    private Long id;
    private String name;
    private String description;
    private boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;

    public CategoryPureVO(Category category) {
        BeanUtils.copyProperties(category, this);
    }
}
