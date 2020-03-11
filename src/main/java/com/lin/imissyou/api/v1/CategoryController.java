package com.lin.imissyou.api.v1;

import com.lin.imissyou.model.Category;
import com.lin.imissyou.model.GridCategory;
import com.lin.imissyou.services.CategoryService;
import com.lin.imissyou.services.GridCategoryService;
import com.lin.imissyou.vo.CategoriesAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoriesAllVO getAll() {
        Map<Integer, List<Category>> map = categoryService.getAll();
        return new CategoriesAllVO(map);
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList() {
        return gridCategoryService.getGridCategoryList();
    }

}
