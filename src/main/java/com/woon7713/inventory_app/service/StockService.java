package com.woon7713.inventory_app.service;

import com.woon7713.inventory_app.dto.StockDto;
import com.woon7713.inventory_app.dto.StockUpdateDto;
import com.woon7713.inventory_app.model.Product;
import com.woon7713.inventory_app.model.Stock;
import com.woon7713.inventory_app.model.Warehouse;
import com.woon7713.inventory_app.query.StockQueryRepository;
import com.woon7713.inventory_app.query.StockSearchCondition;
import com.woon7713.inventory_app.repository.ProductRepository;
import com.woon7713.inventory_app.repository.StockRepository;
import com.woon7713.inventory_app.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final StockQueryRepository queryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;


    public List<Stock> search(StockSearchCondition condition) {
        return queryRepository.search(condition);
    }

    public Stock getById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("재고를 찾을 수 없습니다."));
    }

    public Stock create(StockDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new NoSuchElementException("창고를 찾을 수 없습니다."));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(dto.getQuantity());
        return stockRepository.save(stock);
    }

    public Stock update(Long id, StockUpdateDto dto) {
        Stock stock = getById(id);
        stock.setQuantity(dto.getQuantity());
        return stockRepository.save(stock);
    }

    public void delete(Long id) {
        stockRepository.deleteById(id);
    }

    public void transferStock(Long productId, Long srcWarehouseId, Long destWarehouseId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));
        warehouseRepository.findById(srcWarehouseId)
                .orElseThrow(() -> new NoSuchElementException("출발 창고를 찾을 수 없습니다."));
        Warehouse destWarehouse = warehouseRepository.findById(destWarehouseId)
                .orElseThrow(() -> new NoSuchElementException("도착 창고를 찾을 수 없습니다."));

        Stock srcStock = stockRepository.findByProductIdAndWarehouseId(productId, srcWarehouseId)
                .orElseThrow(() -> new IllegalStateException("출발 재고가 존재하지 않습니다."));
        if (srcStock.getQuantity() < quantity) {
            throw new IllegalStateException("출발 재고가 부족합니다.");
        }
        srcStock.setQuantity(srcStock.getQuantity() - quantity);

        Stock destStock = stockRepository.findByProductIdAndWarehouseId(productId, destWarehouseId)
                .orElse(new Stock());
        destStock.setProduct(product);
        destStock.setWarehouse(destWarehouse);
        destStock.setQuantity(destStock.getQuantity() == null ? quantity : destStock.getQuantity() + quantity);

        stockRepository.saveAll(List.of(srcStock, destStock));
    }





}