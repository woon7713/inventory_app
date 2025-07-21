package com.woon7713.inventory_app.service;

import com.woon7713.inventory_app.dto.WarehouseDto;
import com.woon7713.inventory_app.model.Warehouse;
import com.woon7713.inventory_app.query.WarehouseQueryRepository;
import com.woon7713.inventory_app.query.WarehouseSearchCondition;
import com.woon7713.inventory_app.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseQueryRepository queryRepository; // 검색 디테일을 위해

    public List<Warehouse> search(WarehouseSearchCondition condition) {
        return queryRepository.search(condition);
    }

    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("id를 찾을 수 없습니다. id=" + id));
    }

    public Warehouse create(WarehouseDto dto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        return warehouseRepository.save(warehouse);
    }

    public Warehouse update(Long id, WarehouseDto dto) {
        Warehouse warehouse = getById(id);
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        return warehouseRepository.save(warehouse);
    }

    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }
}