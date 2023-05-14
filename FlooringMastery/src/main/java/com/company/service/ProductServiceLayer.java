package com.company.service;

import com.company.dao.FilePersistenceException;
import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;

public interface ProductServiceLayer {

    List<Products> getAllProducts() throws
            FilePersistenceException;

    List<StateTaxes> getAllStateTaxes() throws
            FilePersistenceException;
    List<Orders> getAllOrders(String date) throws
            FilePersistenceException;
    Orders getOrder(String date, int orderNumber) throws FilePersistenceException;
    void removeOrder(String date, Orders wantedOrder) throws FilePersistenceException;

    boolean verifyOrderNumber(String date, int orderNumber) throws FilePersistenceException;
    boolean createFileExists(String date) throws FilePersistenceException;
    boolean validateState(String state) throws FilePersistenceException;
    boolean validateProduct(String product) throws FilePersistenceException;

    void createNewOrder(String name, String state, String product, Double area, BigDecimal materialCost,
                        BigDecimal labourCost, BigDecimal tax, BigDecimal total, String date, boolean exists) throws FilePersistenceException;

    boolean validDate(String date) throws DateTimeParseException;
    boolean pastDate(String date) throws DateTimeException;
    BigDecimal materialCost(double area,String product) throws FilePersistenceException;
    BigDecimal labourCost(double area, String product) throws FilePersistenceException;
    BigDecimal tax (BigDecimal materialCost, BigDecimal labourCost, String state) throws FilePersistenceException;
    BigDecimal total (BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax);


}
