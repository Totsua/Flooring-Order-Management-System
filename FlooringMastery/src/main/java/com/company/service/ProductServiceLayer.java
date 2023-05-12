package com.company.service;

import com.company.dao.FilePersistenceException;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.util.List;

public interface ProductServiceLayer {

    List<Products> getAllProducts() throws
            FilePersistenceException;

    List<StateTaxes> getAllStateTaxes() throws
            FilePersistenceException;
}
