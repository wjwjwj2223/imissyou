package com.lin.imissyou.repository;

import com.lin.imissyou.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
