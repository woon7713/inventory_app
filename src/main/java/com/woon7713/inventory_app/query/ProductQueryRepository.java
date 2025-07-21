package com.woon7713.inventory_app.query;

import com.woon7713.inventory_app.model.Product;
import com.woon7713.inventory_app.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Product> search(ProductSearchCondition condition) {
        QProduct product = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getName() != null && !condition.getName().isBlank()) {
            builder.and(product.name.containsIgnoreCase(condition.getName()));
        }
        if (condition.getMinPrice() != null) {
            builder.and(product.price.goe(condition.getMinPrice()));
        }
        if (condition.getMaxPrice() != null) {
            builder.and(product.price.loe(condition.getMaxPrice()));
        }

        return queryFactory.selectFrom(product).where(builder).orderBy(product.name.asc()).fetch();
    }
}