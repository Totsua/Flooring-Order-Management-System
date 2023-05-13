package com.company.dao;

import com.company.model.Orders;

import java.util.List;

public interface OrdersDAO {
    List<Orders> getAllOrders() throws FilePersistenceException;
}
