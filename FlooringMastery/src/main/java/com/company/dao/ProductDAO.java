package com.company.dao;

import com.company.model.Products;

import java.util.List;

public interface ProductDAO {
    List<Products> getAllProducts() throws FilePersistenceException;
    boolean validateProduct(String product) throws FilePersistenceException;
    Products getProduct(String product) throws FilePersistenceException;

}
