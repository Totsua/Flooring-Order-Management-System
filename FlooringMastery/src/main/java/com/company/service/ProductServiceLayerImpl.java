package com.company.service;

import com.company.dao.*;
import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.util.List;

// Used as the in-between for the Controller and DAO

public class ProductServiceLayerImpl implements ProductServiceLayer {
    ProductDAO dao;
    StateTaxesDAO dao2;
    OrdersDAO dao3;
    public ProductServiceLayerImpl(ProductDAO dao, StateTaxesDAO dao2, OrdersDAO dao3){this.dao=dao; this.dao2 = dao2; this.dao3 = dao3;}

    @Override
    public List<Products> getAllProducts () throws FilePersistenceException {
        return dao.getAllProducts();
    }

    @Override
    public List<StateTaxes> getAllStateTaxes() throws FilePersistenceException {
        return dao2.getAllStateTax();
    }

    @Override
    public List<Orders> getAllOrders() throws FilePersistenceException {
        return dao3.getAllOrders();
    }



}
