package com.company.dao;

import com.company.model.Orders;

import java.util.List;

public interface OrdersDAO {
    List<Orders> getAllOrders(String date) throws FilePersistenceException;
    Orders addOrder(String date, Orders order) throws FilePersistenceException;
}
