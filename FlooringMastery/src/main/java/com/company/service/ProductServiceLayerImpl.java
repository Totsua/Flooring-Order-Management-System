package com.company.service;

import com.company.dao.FilePersistenceException;
import com.company.dao.ProductDAO;
import com.company.dao.StateTaxesDAO;
import com.company.model.Products;
import com.company.model.StateTaxes;
import com.company.service.ProductServiceLayer;

import java.util.List;

public class ProductServiceLayerImpl implements ProductServiceLayer {
    ProductDAO dao;
    StateTaxesDAO dao2;
    public ProductServiceLayerImpl(ProductDAO dao, StateTaxesDAO dao2){this.dao=dao; this.dao2 = dao2;}

    @Override
    public List<Products> getAllProducts() throws FilePersistenceException {
        return dao.getAllProducts();
    }

    @Override
    public List<StateTaxes> getAllStateTaxes() throws FilePersistenceException {
        return dao2.getAllStateTax();
    }
}
