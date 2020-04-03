package com.lin.imissyou.repository;

import com.lin.imissyou.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date now, int Status, Long uid, Pageable pageable);

}
