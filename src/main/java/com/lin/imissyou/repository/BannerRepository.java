package com.lin.imissyou.repository;

import com.lin.imissyou.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    Banner findOneById(String id);
    Banner findOneByName(String name);
}
