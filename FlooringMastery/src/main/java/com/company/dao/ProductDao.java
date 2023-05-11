package com.company.dao;

import com.company.model.Products;

import java.util.List;

public interface ProductDao {
    List<Products> getAllProducts() throws ProductPersistenceException;

}
