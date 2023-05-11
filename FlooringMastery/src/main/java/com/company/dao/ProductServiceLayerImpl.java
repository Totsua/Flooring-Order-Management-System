package com.company.dao;

import com.company.model.Products;

import java.util.List;

public class ProductServiceLayerImpl implements ProductServiceLayer{
    ProductDao dao;
    public ProductServiceLayerImpl(ProductDao dao){this.dao=dao;}
    @Override
    public List<Products> getAllProducts() throws ProductPersistenceException {
        return dao.getAllProducts();
    }
}
