package com.lin.imissyou.services;

import com.lin.imissyou.model.Spu;
import com.lin.imissyou.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuService {

    @Autowired
    SpuRepository spuRepository;

    public Spu getSpu(Long id) {
        return spuRepository.findOneById(id);
    }

    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        return spuRepository.findAll(pageable);
    }

    public Page<Spu> getByCategory(Long cid, Boolean isRoot, Integer pageNum, Integer size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        if (isRoot) {
            return this.spuRepository.findByRootCategoryIdOrderByCreateTimeDesc(cid, pageable);
        } else {
            return this.spuRepository.findByCategoryIdOrderByCreateTimeDesc(cid, pageable);
        }
    }
}

