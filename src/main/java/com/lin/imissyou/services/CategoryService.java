package com.lin.imissyou.services;

import com.lin.imissyou.model.Category;
import com.lin.imissyou.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Map<Integer, List<Category>> getAll() {
        List<Category> roots = categoryRepository.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs = categoryRepository.findAllByIsRootOrderByIndexAsc(false);
        HashMap<Integer, List<Category>> hashMap = new HashMap<>();
        hashMap.put(1, roots);
        hashMap.put(2, subs);
        return hashMap;
    }
}
