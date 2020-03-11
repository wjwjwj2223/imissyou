package com.lin.imissyou.repository;

import com.lin.imissyou.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsRootOrderByIndexAsc(Boolean isRoot);

}
