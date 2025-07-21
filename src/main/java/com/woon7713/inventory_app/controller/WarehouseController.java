package com.woon7713.inventory_app.controller;

import com.woon7713.inventory_app.dto.WarehouseDto;
import com.woon7713.inventory_app.model.Warehouse;
import com.woon7713.inventory_app.query.WarehouseSearchCondition;
import com.woon7713.inventory_app.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> search(WarehouseSearchCondition condition) {
        return warehouseService.search(condition);
    }

    @GetMapping("/{id}")
    public Warehouse getById(@PathVariable Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping
    public Warehouse create(@Valid @RequestBody WarehouseDto dto) {
        return warehouseService.create(dto);
    }

    @PutMapping("/{id}")
    public Warehouse update(@PathVariable Long id, @Valid @RequestBody WarehouseDto dto) {
        return warehouseService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        warehouseService.delete(id);
    }

}