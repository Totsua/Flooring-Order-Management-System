package com.company.controller;

import com.company.dao.OrdersDAO;
import com.company.dao.OrdersDAOFileImpl;
import com.company.dao.ProductDAOFileImpl;
import com.company.dao.FilePersistenceException;
import com.company.model.Orders;
import com.company.model.StateTaxes;
import com.company.service.ProductServiceLayer;
import com.company.model.Products;
import com.company.view.ObjectView;

import java.util.List;

public class FlooringMasteryController {

// Controller should talk to the Service Layer not DAO
    // The Service Layer is the ONLY component allowed to talk to the DAO


    private ObjectView view;
    private ProductServiceLayer service;
public  FlooringMasteryController(ProductServiceLayer service, ObjectView view){
    this.view = view;
    this.service = service;
}
OrdersDAOFileImpl ordersDAO = new OrdersDAOFileImpl();


// Main Method of the Controller
    // Calls the main menu and keeps user there until a valid input is chosen
public void run(){
    boolean stayOn = true;
    int MenuChoice = 0;
    try{
        while(stayOn){
            MenuChoice = getMenuSelect();
            switch (MenuChoice){
                case 1:
                    viewOrder(); // TESTING
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    exportMessage();
                    break;
                case 6:
                    stayOn = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
        }
         exitMessage(); // Exit message once the user chooses "Quit"
    }catch (FilePersistenceException e){
        view.displayErrorMessage(e.getMessage());
    }
}

// Main Menu Command Methods
    private int getMenuSelect() {return view.PrintMenuAndGetChoice();}

// Main Menu Messages
    private void unknownCommand(){view.displayUnknownCommandMessage();}
    private void exitMessage(){view.displayExitMessage();}
    private void exportMessage(){view.displayExportMessage();}


private void viewOrder() throws FilePersistenceException{
    List<Orders> ordersList = service.getAllOrders();
    view.displayOrders(ordersList);
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
