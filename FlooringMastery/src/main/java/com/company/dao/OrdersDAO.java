package com.company.dao;

import com.company.model.Orders;

import java.util.List;

public interface OrdersDAO {
    List<Orders> getAllOrders(String date) throws FilePersistenceException;
    boolean createFileExists(String date) throws FilePersistenceException;
    void newOrderNewFile(Orders order, String date) throws FilePersistenceException;
}
