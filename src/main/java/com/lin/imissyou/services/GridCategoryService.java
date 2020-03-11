package com.lin.imissyou.services;

import com.lin.imissyou.model.GridCategory;
import com.lin.imissyou.repository.GridCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridCategoryService {

    @Autowired
    GridCategoryRepository gridCategoryRepository;

    public List<GridCategory> getGridCategoryList() {
        return gridCategoryRepository.findAll();
    }
}
