package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Optional<Product> getProductById(long id);

    List<Product> getAllProducts();

    void updateProduct(Product product, long id);

    void deleteProductById(long id);

    List<Product> getProductsWithOffsetLimitOrder(int offset, int limit, String price);

    List<Product> getProductsWithOffsetLimit(int offset, int limit);

    List<Product> getProductsWithOffsetLimitOrderSearch(int offset, int limit, String id, String searchParam);

    int getCountOfProducts();

    List<Product> getProductsByCategory(int idCategory);

    void saveProduct(Product product);

    List<Product> getProductsByProducer(int idProducer);

    List<Product> getProductsWithOrderAndSearch(String sortParam, String searchParam);

    int getCountOfProductsWithSearch(String searchParam);

    List<Product> getProductsByCategoryWithOffsetLimitOrder(int offset, int countProductsOnProductPage, String sortParam, int idCategoryParam);

    int getCountOfProductsByCategory(int idCategoryParam);

    List<Product> getProductsByProducerWithOffsetLimitOrder(int offset, int countProductsOnProductPage, String sortParam, int idProducer);

    int getCountOfProductsByProducer(int idProducer);
}