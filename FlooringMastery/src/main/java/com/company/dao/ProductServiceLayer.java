package com.company.dao;

import com.company.dao.ProductPersistenceException;
import com.company.model.Products;

import java.util.List;

public interface ProductServiceLayer {

    List<Products> getAllProducts() throws
            ProductPersistenceException;
}
