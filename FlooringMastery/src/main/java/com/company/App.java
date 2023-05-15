package com.company;

import com.company.dao.*;

import com.company.controller.FlooringMasteryController;
import com.company.service.ServiceLayer;
import com.company.service.ServiceLayerImpl;
import com.company.view.ObjectView;
import com.company.view.UserIO;
import com.company.view.UserIOConsoleImpl;

// Turning on the app
public class App {
    public static void main(String[] args){
        UserIO myIo = new UserIOConsoleImpl();
        ProductDAO prodDao= new ProductDAOFileImpl();
        StateTaxesDAO taxDAO = new StateTaxesDaoFileImpl();
        OrdersDAO ordersDAO = new OrdersDAOFileImpl();
        ServiceLayer myService = new ServiceLayerImpl(prodDao, taxDAO,ordersDAO );
        ObjectView myView  = new ObjectView(myIo);
        FlooringMasteryController floor = new FlooringMasteryController(myService, myView);
        floor.run();
    }
}
