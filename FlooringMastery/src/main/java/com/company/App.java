package com.company;

import com.company.dao.*;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.company.model.Products.allProducts;
import static com.company.model.StateTaxes.allStateTaxes;
import com.company.controller.FlooringMasteryController;
import com.company.view.ProductView;

public class App {
    public static void main(String[] args) throws ProductPersistenceException {
        ProductDao prodDao= new ProductDaoFileImpl();
        ProductDaoFileImpl trueDao = new ProductDaoFileImpl();
        ProductServiceLayer myService = new ProductServiceLayerImpl(prodDao);
        ProductView myView  = new ProductView();
        FlooringMasteryController floor = new FlooringMasteryController(myService, myView);
        floor.run();


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
