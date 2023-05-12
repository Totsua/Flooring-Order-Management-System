package com.company;

import com.company.dao.*;

import com.company.controller.FlooringMasteryController;
import com.company.service.ProductServiceLayer;
import com.company.service.ProductServiceLayerImpl;
import com.company.view.ObjectView;
import com.company.view.UserIO;
import com.company.view.UserIOConsoleImpl;

public class App {
    public static void main(String[] args) throws FilePersistenceException {
        UserIO myIo = new UserIOConsoleImpl();
        ProductDAO prodDao= new ProductDAOFileImpl();
        StateTaxesDAO taxDAO = new StateTaxesDaoFileImpl();
        ProductDAOFileImpl trueDao = new ProductDAOFileImpl();
        ProductServiceLayer myService = new ProductServiceLayerImpl(prodDao, taxDAO);
        ObjectView myView  = new ObjectView(myIo);
        FlooringMasteryController floor = new FlooringMasteryController(myService, myView);
        floor.run();
        //System.out.println(getAllStateTax);


       /* Products product = new Products("Mongo", "1.00", "5.20");
        StateTaxes taxes = new StateTaxes("TX", "Texas", "5.00");

        product.setProductType("Boba Fetty");
        //taxes.setStateAlphaCode("LA");

        System.out.println(allProducts.toString());
        System.out.println(allStateTaxes.toString());

        BigDecimal a = new BigDecimal("53.459");
        BigDecimal x = a.setScale(2, RoundingMode.HALF_UP);
        System.out.println(x);
*/
    }
}
