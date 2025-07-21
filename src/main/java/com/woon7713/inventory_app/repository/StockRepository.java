package com.woon7713.inventory_app.repository;

import com.woon7713.inventory_app.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, QuerydslPredicateExecutor<Stock> {
    Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
}