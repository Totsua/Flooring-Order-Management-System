package com.company.controller;

import com.company.dao.ProductDaoFileImpl;
import com.company.dao.ProductPersistenceException;
import com.company.dao.ProductServiceLayer;
import com.company.model.Products;
import com.company.view.ProductView;

import java.util.List;

public class FlooringMasteryController {

// Controller should talk to the Service Layer not DAO
    // The Service Layer is the ONLY component allowed to talk to the DAO

ProductDaoFileImpl dao = new ProductDaoFileImpl();
    private ProductView view;
    private ProductServiceLayer service;
public  FlooringMasteryController(ProductServiceLayer service, ProductView view){
    this.view = view;
    this.service = service;
}

public void run() throws ProductPersistenceException {
    viewProducts();
    System.out.println();
}

   private void viewProducts() throws ProductPersistenceException{
       List<Products>  ProductList = service.getAllProducts();
       view.displayProductList(ProductList);
   }

}
