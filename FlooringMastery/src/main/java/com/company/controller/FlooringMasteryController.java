package com.company.controller;

import com.company.dao.ProductDAOFileImpl;
import com.company.dao.FilePersistenceException;
import com.company.model.StateTaxes;
import com.company.service.ProductServiceLayer;
import com.company.model.Products;
import com.company.view.ObjectView;

import java.util.List;

public class FlooringMasteryController {

// Controller should talk to the Service Layer not DAO
    // The Service Layer is the ONLY component allowed to talk to the DAO

ProductDAOFileImpl dao = new ProductDAOFileImpl();
    private ObjectView view;
    private ProductServiceLayer service;
public  FlooringMasteryController(ProductServiceLayer service, ObjectView view){
    this.view = view;
    this.service = service;
}

public void run() throws FilePersistenceException {
    viewProducts();
    viewTaxes();
    System.out.println();
}

   private void viewProducts() throws FilePersistenceException {
       List<Products>  ProductList = service.getAllProducts();
       view.displayProductList(ProductList);
   }
   private void viewTaxes() throws FilePersistenceException{
    List<StateTaxes> stateTaxesList = service.getAllStateTaxes();
    view.displayStateTaxes(stateTaxesList);
   }

}
