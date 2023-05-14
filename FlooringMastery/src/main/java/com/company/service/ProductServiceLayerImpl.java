package com.company.service;

import com.company.dao.*;
import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

// Used as the in-between for the Controller and DAO

public class ProductServiceLayerImpl implements ProductServiceLayer {
    ProductDAO dao;
    StateTaxesDAO dao2;
    OrdersDAO dao3;

    public ProductServiceLayerImpl(ProductDAO dao, StateTaxesDAO dao2, OrdersDAO dao3) {
        this.dao = dao;
        this.dao2 = dao2;
        this.dao3 = dao3;
    }

    @Override
    public List<Products> getAllProducts() throws FilePersistenceException {
        return dao.getAllProducts();
    }

    @Override
    public List<StateTaxes> getAllStateTaxes() throws FilePersistenceException {
        return dao2.getAllStateTax();
    }

    // Method to get all dates from certain date
    @Override
    public List<Orders> getAllOrders(String date) throws FilePersistenceException {
        return dao3.getAllOrders(date);
    }

    // Method to check if the date inputted is a valid date
    @Override
    public boolean validDate(String date) throws DateTimeParseException {
        boolean isValid = true;
        // Have to set the formatter to STRICT otherwise it tries to correct incorrect dates
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-d-uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(date,format);
        } catch (DateTimeParseException e) {
            isValid = false;
            System.out.println("Invalid date. \n" + e.getMessage());
        }
        return isValid;
    }

}