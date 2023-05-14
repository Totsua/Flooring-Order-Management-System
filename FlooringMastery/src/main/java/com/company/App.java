package com.company;

import com.company.dao.*;

import com.company.controller.FlooringMasteryController;
import com.company.service.ProductServiceLayer;
import com.company.service.ProductServiceLayerImpl;
import com.company.view.ObjectView;
import com.company.view.UserIO;
import com.company.view.UserIOConsoleImpl;

public class App {
    public static void main(String[] args){
        UserIO myIo = new UserIOConsoleImpl();
        ProductDAO prodDao= new ProductDAOFileImpl();
        StateTaxesDAO taxDAO = new StateTaxesDaoFileImpl();
        OrdersDAO ordersDAO = new OrdersDAOFileImpl();
        ProductServiceLayer myService = new ProductServiceLayerImpl(prodDao, taxDAO,ordersDAO );
        ObjectView myView  = new ObjectView(myIo);
        FlooringMasteryController floor = new FlooringMasteryController(myService, myView);
        floor.run();
    }
}
