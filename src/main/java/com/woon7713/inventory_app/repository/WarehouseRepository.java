package com.woon7713.inventory_app.repository;

import com.woon7713.inventory_app.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long>, QuerydslPredicateExecutor<Warehouse> {
}
