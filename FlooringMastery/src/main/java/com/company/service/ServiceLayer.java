package com.company.service;

import com.company.dao.FilePersistenceException;
import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;

public interface ServiceLayer {

    List<Products> getAllProducts() throws FilePersistenceException;

    List<StateTaxes> getAllStateTaxes() throws FilePersistenceException;

    List<Orders> getAllOrders(String date) throws FilePersistenceException;

    Orders getOrder(String date, int orderNumber) throws FilePersistenceException;

    Orders preEditOrder(String date, int orderNumber, String orderChanges) throws FilePersistenceException;

    void editOrder(String date, Orders editedOrder, int orderNumber) throws FilePersistenceException;

    void removeOrder(String date, Orders wantedOrder) throws FilePersistenceException;

    boolean verifyOrderNumber(String date, int orderNumber) throws FilePersistenceException;

    boolean createFileExists(String date) throws FilePersistenceException;

    void createNewOrder(String name, String state, String product, Double area, BigDecimal materialCost,
                        BigDecimal labourCost, BigDecimal tax, BigDecimal total, String date, boolean exists)
            throws FilePersistenceException;

    boolean validDate(String date) throws DateTimeParseException;

    boolean pastDate(String date) throws DateTimeException;

    // THESE COULD/SHOULD HAVE BEEN PRIVATE METHODS OR BEEN A PUBLIC METHOD CALLED "CALCULATIONS"
    // But due to time constraints I could not do this when I realised.
    BigDecimal materialCost(double area, String product) throws FilePersistenceException;

    BigDecimal labourCost(double area, String product) throws FilePersistenceException;

    BigDecimal tax(BigDecimal materialCost, BigDecimal labourCost, String state) throws FilePersistenceException;

    BigDecimal total(BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax);


}
