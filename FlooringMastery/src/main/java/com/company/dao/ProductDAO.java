package com.company.dao;

import com.company.model.Products;

import java.util.List;

public interface ProductDAO {
    List<Products> getAllProducts() throws FilePersistenceException;

}
