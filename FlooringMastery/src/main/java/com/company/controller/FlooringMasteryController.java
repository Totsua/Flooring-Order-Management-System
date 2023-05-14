package com.company.controller;

import com.company.dao.FilePersistenceException;
import com.company.model.Orders;
import com.company.model.StateTaxes;
import com.company.service.ProductServiceLayer;
import com.company.model.Products;
import com.company.view.ObjectView;

import java.util.List;

public class FlooringMasteryController {

    // Controller should talk to the Service Layer and View not DAO
    // The Service Layer is the ONLY component allowed to talk to the DAO

    private ObjectView view;
    private ProductServiceLayer service;
public  FlooringMasteryController(ProductServiceLayer service, ObjectView view){
    this.view = view;
    this.service = service;
}


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
                    viewOrder(); // Display the Orders
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


private void viewOrder() throws FilePersistenceException {
    // Ask user to input a date
    String myDate= view.displayAskDate();
    // If user doesn't confirm the date, go back to the main menu.
    // If they do verify if the date is valid.
    if (myDate != null) {
        boolean dateValid = service.validDate(myDate);
        // If the date is valid, check to see if there are orders for that given date.
        if (dateValid) {
            try {
                List<Orders> ordersList = service.getAllOrders(myDate);
                view.displayOrders(ordersList);
            } catch (FilePersistenceException e){
                // If there's no file print the error message
                view.displayErrorMessage(e.getMessage());
            }
        }
    }
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
