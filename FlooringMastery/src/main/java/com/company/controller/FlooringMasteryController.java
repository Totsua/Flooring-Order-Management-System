package com.company.controller;

import com.company.dao.FilePersistenceException;
import com.company.model.Orders;
import com.company.model.StateTaxes;
import com.company.service.ServiceLayer;
import com.company.model.Products;
import com.company.view.ObjectView;

import java.math.BigDecimal;
import java.util.List;

public class FlooringMasteryController {

    // Controller should talk to the Service Layer and View not DAO
    // The Service Layer is the ONLY component allowed to talk to the DAO

    private ObjectView view;
    private ServiceLayer service;

    public FlooringMasteryController(ServiceLayer service, ObjectView view) {
        this.view = view;
        this.service = service;
    }


    // Main Method of the Controller
    // Calls the main menu and keeps user there until a valid input is chosen
    public void run() {
        boolean stayOn = true;
        int MenuChoice = 0;
        try {
            while (stayOn) {
                MenuChoice = getMenuSelect();
                switch (MenuChoice) {
                    case 1:
                        viewOrder(); // Display the Orders
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
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
        } catch (FilePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    // Main Menu Command Methods
    private int getMenuSelect() {
        return view.PrintMenuAndGetChoice();
    }

    // Main Menu Messages
    private void unknownCommand() {
        view.displayUnknownCommandMessage();
        view.displayPauseMessage();
    }

    private void exitMessage() {
        view.displayExitMessage();
    }

    private void exportMessage() {
        view.displayExportMessage();
        view.displayPauseMessage();
    }


    private void viewOrder() throws FilePersistenceException {
        // Ask user to input a date
        view.displayOrdersTitle();
        String myDate = view.displayAskDate();
        // If user doesn't confirm the date, go back to the main menu.
        // If they do verify if the date is valid, if it's not back to the main menu.
        boolean dateValid = service.validDate(myDate);
        // If the date is valid, check to see if there are orders for that given date.
        if (dateValid) {
            try {
                List<Orders> ordersList = service.getAllOrders(myDate);
                view.displayOrders(ordersList);
            } catch (FilePersistenceException e) {
                // If there's no file print the error message
                view.displayErrorMessage(e.getMessage());
            }
        }
        // Pausing the program until the user continues
        view.displayPauseMessage();
    }


    // Method for adding an order
    private void addOrder() throws FilePersistenceException {
        boolean inPast;
        boolean orderConfirm;
        boolean fileExists;
        List<Products> productList = service.getAllProducts();
        List<StateTaxes> stateList = service.getAllStateTaxes();
        // Display the chosen option
        view.displayAddOrderTitle();
        // Ask for the date
        String myDate = view.displayAskDate();
        // See if it's an actual date - if it's not - back to main menu
        boolean dateValid = service.validDate(myDate);
        if (dateValid) {
            // Verify it's a future date - back to main menu if not
            inPast = service.pastDate(myDate);
            if (!inPast) {
                // Get the Customer Name, State, Product and Area
                String name = view.getCustomerName();
                String state = view.getCustomerState(stateList);
                String product = view.getCustomerProduct(productList);
                Double area = view.getCustomerArea();
                // Calculations for costs, could be done in one method.
                // Decided to split it, just to make it easier to spot an error
                BigDecimal materialCost = service.materialCost(area, product);
                BigDecimal labourCost = service.labourCost(area, product);
                BigDecimal tax = service.tax(materialCost, labourCost, state);
                BigDecimal total = service.total(materialCost, labourCost, tax);
                // Confirm the Order - or back to main menu
                // (Realised too late that the order number can't be displayed due to the way Order objects are created
                // but the customer knows the date they can see number like that)
                orderConfirm = view.displayCreatedOrder(name, state, product, area,
                        materialCost, labourCost, tax, total);
                // Check if file already exists (This should be done in service but too pressed for time to change)
                if (orderConfirm) {
                    fileExists = service.createFileExists(myDate);
                    // Create the new Order
                    service.createNewOrder(name, state, product, area, materialCost,
                            labourCost, tax, total, myDate, fileExists);
                    // Show it was successful
                    view.displayAddOrderSuccess();
                }
            }
        }
        // Pausing the program until the user continues
        view.displayPauseMessage();
    }


    // Method for editing an order
    private void editOrder() throws FilePersistenceException {
        List<Products> productList = service.getAllProducts();
        List<StateTaxes> stateTaxesList = service.getAllStateTaxes();
        // Display the title
        view.displayEditOrderTitle();
        // Ask for the date
        String myDate = view.displayAskDate();
        // Verify it's an actual date - if not back to main menu
        boolean dateValid = service.validDate(myDate);
        if (dateValid) {
            // Now to verify if there is a for that date - if not back to main menu
            boolean fileExists = service.createFileExists(myDate);
            if (fileExists) {
                // Ask for the order number
                int orderNumber = view.askOrderNumber();
                // Verify if there is an order with that number - if not back to main menu
                boolean validOrderNum = service.verifyOrderNumber(myDate, orderNumber);
                if (validOrderNum) {
                    // Getting the Order object
                    Orders wantedOrder = service.getOrder(myDate, orderNumber);
                    // Asking user for changes
                    String orderChanges = view.displayEditingOrder(wantedOrder, stateTaxesList, productList);
                    // Creating an Order object
                    Orders editedOrder = service.preEditOrder(myDate, orderNumber, orderChanges);
                    // Confirming the change, if not back to main menu
                    boolean confirmChange = view.displayChangedOrder(editedOrder);
                    if (confirmChange) {
                        service.editOrder(myDate, editedOrder, orderNumber);
                        // Show the edit was successful
                        view.displayEditOrderSuccess();
                    }
                }
            }
        }
        // Pausing the program until the user continues
        view.displayPauseMessage();
    }

    private void removeOrder() throws FilePersistenceException {
        // Display the title
        view.displayRemoveOrderTitle();
        // Ask user to input date
        String myDate = view.displayAskDate();
        // Verify the date is real, if not back to main menu
        boolean dateValid = service.validDate(myDate);
        if (dateValid) {
            // Verify there is a file with that date name, if not back to main menu
            boolean fileExists = service.createFileExists(myDate);
            if (fileExists) {
                // Ask user for the order number
                int orderNumber = view.askOrderNumber();
                // Verify if there is an order with that number, if not back to main menu
                boolean validOrderNum = service.verifyOrderNumber(myDate, orderNumber);
                if (validOrderNum) {
                    // Get the Orders object
                    Orders wantedOrder = service.getOrder(myDate, orderNumber);
                    // Display and confirm if the user wants to remove the order - if not back to main menu
                    boolean delete = view.displayWantedRemovedOrder(wantedOrder);
                    if (delete) {
                        // If they confirm then delete the order
                        service.removeOrder(myDate, wantedOrder);
                        // Show then delete was successful
                        view.displayRemoveSuccessMessage();
                    }
                }
            }
        }
        // Pausing the program until the user continues
        view.displayPauseMessage();
    }

}
