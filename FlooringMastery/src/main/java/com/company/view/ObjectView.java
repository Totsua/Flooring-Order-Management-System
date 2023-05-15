package com.company.view;

import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.math.BigDecimal;
import java.util.List;

public class ObjectView {

    private UserIO io;

    public ObjectView(UserIO io) {
        this.io = io;
    }

    // The Main Menu itself - with capability to input a valid choice from the user
    public int PrintMenuAndGetChoice() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readInt("Please Input An Option [1-6]", 1, 6);
    }

    // MENU MESSAGES

    // Displaying Orders Title
    public void displayOrdersTitle() {
        io.print("* * * * * * * * * * * * Display Orders * * * * * * * * * * * * * * * *");
    }

    // Asking the user for a date and seeing if they want to confirm it
    // If they don't confirm it, keep asking
    public String displayAskDate() {
        boolean confirm;
        String orderDate;
        // do loop to keep going
        do {
            io.print("What Is The Date Of The Order:");
            String day = io.readDay("Please input the day of the order (DD): ", 1, 31);
            String month = io.readMonth("Please input the month of the order (MM): ", 1, 12);
            String year = io.readYear("Please input the year of the order (YYYY): ");
            orderDate = month + "-" + day + "-" + year;
            // To Show/Confirm the date
            confirm = io.readChoice(orderDate + " (MM-DD-YYYY) is this correct?");
        } while (!confirm);
        return orderDate;
    }


    // Title for adding an order
    public void displayAddOrderTitle() {
        io.print("* * * * * * * * * * * * * * Add An Order * * * * * * * * * * * * * * *");
    }

    // Asking the user for a Name
    public String getCustomerName() {
        return io.readName("Please Enter Your Name");
    }

    // Asking/Verifying the "State" input of the user
    public String getCustomerState(List<StateTaxes> statesList) {
        boolean confirmState;
        String state;
        // do loop to keep going until state is valid
        do {
            int wrongCounter = 0;
            confirmState = false;
            // Show the states and tax rates
            displayStateTaxes(statesList);
            state = io.readString("Please Enter Your State Alpha Code: ");
            // Check to see if the input is the same as one in the list
            // (Would be more efficient to put this in a private separated method because it's used more than once)
            for (StateTaxes states : statesList) {
                // Iterate through the statesList and check to see if the Alpha Code is the same
                // If it is - set the state to the one in the list
                if (states.getStateAlphaCode().equalsIgnoreCase(state)) {
                    confirmState = true;
                    state = states.getStateAlphaCode();
                } else {
                    wrongCounter++;
                }
                // If the input was not equal to any of the Alpha Codes then say it's incorrect
                if (wrongCounter >= statesList.size()) {
                    io.print("Invalid State Code. Please Try Again.");
                }
            } // end of iterating for loop
        } while (!confirmState);
        return state;

        // Attempted Lambda and Stream - No time to get it working
            /*String inThere = statesList.stream()
                    .filter(s -> s.getStateAlphaCode().equalsIgnoreCase(state)
                    .findFirst()
                    .orElse(null);*/

    }


    // Asking/Verifying the Product a customer wants
    public String getCustomerProduct(List<Products> productsList) {
        boolean confirmProduct;
        String productType;
        do {
            int wrongCounter = 0;
            confirmProduct = false;
            // Show all the products
            displayProductList(productsList);
            productType = io.readString("Please Enter A Product Type: ");
            // Iterate through the products and see if the input is equal to one
            // (Would be more efficient to put this in a private separated method because it's used more than once)
            for (Products product : productsList) {
                if (product.getProductType().equalsIgnoreCase(productType)) {
                    // If input is in list, set the input to the one in the list.
                    confirmProduct = true;
                    productType = product.getProductType();
                } else {
                    wrongCounter++;
                }
                // If none of the products match the input - tell the user to try again
                if (wrongCounter == productsList.size()) {
                    io.print("Invalid Product. Please Try Again.");
                }
            }
        } while (!confirmProduct);
        return productType;
    }


    // Asking/Verifying the Area the customer wants.
    public Double getCustomerArea() {
        return io.readArea("Please Enter The Area In SqFt (Min 100)", 100);
    }

    // Displaying/Verifying the current order to be created.
    public boolean displayCreatedOrder(String name, String state, String product, Double area, BigDecimal materialCost,
                                       BigDecimal labourCost, BigDecimal tax, BigDecimal total) {
        System.out.println("Customer: " + name + ", State: " + state + ", Product: " + product + ", Area: " + area +
                "SqFt, MaterialCost: $" + materialCost + ", LabourCost: $" + labourCost + ", Tax: $" + tax +
                "\n + Total: $" + total);
        return io.readChoice("Do You Want To Place The Order?");
    }

    // Showing the customer that the order added was successful
    public void displayAddOrderSuccess() {
        io.print("* * * * * * * * * * * Order Added Successfully  * * * * * * * * * * * *");
    }

    // Display Edit Orders Title
    public void displayEditOrderTitle() {
        io.print("* * * * * * * * * * * * Edit An Order * * * * * * * * * * * * * * * * *");
    }

    // Displaying/Asking/Verifying the inputs of an edited order
    // Some of the loops could be put into a separate method
    public String displayEditingOrder(Orders order, List<StateTaxes> statesList, List<Products> productsList) {
        boolean confirmState;
        boolean confirmProduct;
        boolean confirmChanges = false;
        String orderChanges;
        String name;
        String state;
        String productType;
        double area;
        String areaString = String.valueOf(order.getArea());
        // The loop keeps going until the user confirms the changes
        do {
            // Get the customer name and show the one already on the order
            name = io.readName("Please New Enter Customer Name (" + order.getCustomerName() + "): ",
                    order.getCustomerName());
            // do loop to verify the State inputted
            // (Would be more efficient to put this in a private separated method because it's used more than once)
            do {
                confirmState = false;
                // Show the taxes
                displayStateTaxes(statesList);
                state = io.readEditParameter("Please Enter A New State Alpha Code ("
                        + order.getState() + ")", order.getState());
                // Iterate through list and verify if the input is in it
                for (StateTaxes states : statesList) {
                    if (states.getStateAlphaCode().equalsIgnoreCase(state)) {
                        // If input is in list, set the input to the one in the list.
                        confirmState = true;
                        state = states.getStateAlphaCode();
                    }
                }
            } while (!confirmState);

            // do loop to verify the product inputted
            // (Would be more efficient to put this in a private separated method because it's used more than once)
            do {
                confirmProduct = false;
                // Display the products
                displayProductList(productsList);
                productType = io.readEditParameter("Please Enter A New Product Type (" + order.getProductType()
                        + ")", order.getProductType());
                // Iterate through the products and see if the input is in there
                for (Products product : productsList) {
                    if (product.getProductType().equalsIgnoreCase(productType)) {
                        // If input is in list, set the input to the one in the list.
                        confirmProduct = true;
                        productType = product.getProductType();
                    }
                }
            } while (!confirmProduct);

            // Asking/Verifying the Area inputted
            area = io.readArea("Please Enter The Desired Area (" + areaString + ")", 100);

            // Display and confirm the change from the user
            confirmChanges = io.readChoice("(" + name + "), (" + state + "), (" + productType + "), ("
                    + area + ")" +
                    "\nWould You Like To Make These Changes?");
        } while (!confirmChanges);
        // Return the string values of the changes
        orderChanges = name + "," + state + "," + productType + "," + String.valueOf(area);
        return orderChanges;
    }


    // Display/Confirm the whole edited and changed order
    public boolean displayChangedOrder(Orders editedOrder) {
        System.out.println("Customer: " + editedOrder.getCustomerName() + ", State: " + editedOrder.getState().toUpperCase() +
                ", Product: " + editedOrder.getProductType() + ", Area: " + editedOrder.getArea() +
                "SqFt, MaterialCost: $" + editedOrder.getMaterialCost() + ", LabourCost: $" + editedOrder.getLabourCost()
                + ", Tax: $" + editedOrder.getTax() +
                "\nTotal: $" + editedOrder.getTotal());
        return io.readChoice("Do You Want To Change The Order?");
    }

    // Display the edit was successful
    public void displayEditOrderSuccess(){
        io.print("* * * * * * * * * * * Order Edited Successfully * * * * * * * * * * * *");
    }

    // Display Remove Order Title
    public void displayRemoveOrderTitle() {
        io.print("* * * * * * * * * * * * Remove An Order * * * * * * * * * * * * * * * *");
    }

    // Displaying and confirming the order the user wants to remove
    public boolean displayWantedRemovedOrder(Orders order) {
        System.out.println("Order No." + order.getOrderNumber() + ", Customer: " + order.getCustomerName() + ", State: " + order.getState() +
                ", TaxRate: " + order.getStateTaxRate() + "%, Product: " + order.getProductType() + ", " +
                "Area: " + order.getArea() + "SqFt, CostPerSqFt: $" + order.getCostPerSqFoot() +
                ", LabourCostPerSqFt: $" + order.getLabourPerSqFoot() + ", MaterialCost: $" + order.getMaterialCost() +
                ", LabourCost: $" + order.getLabourCost() + ", Tax: $" + order.getTax() + ", Total: $" + order.getTotal());
        return io.readChoice("Do You Wish To Remove This Order? ");
    }

    // If the removal has gone through display that it's successful
    public void displayRemoveSuccessMessage() {
        io.print("* * * * * * * * * * * * Remove Successful * * * * * * * * * * * * * * * *");
    }


    // Asking the user for the order number
    public int askOrderNumber() {
        return io.readInt("Please Input The Order Number : ");
    }

    // "Quit" menu option message
    public void displayExitMessage() {
        io.print("Thank You For Using This Service!");
        io.print("Goodbye!");
    }

    // Unknown command message
    public void displayUnknownCommandMessage() {
        io.print("Command Unknown");
    }

    // Error Message
    public void displayErrorMessage(String errorMessage) {
        io.print("* * * * * * * * * * * * * * ERROR * * * * * * * * * * * * * * * * * *");
        io.print(errorMessage);
    }

    // "Export All Data" message -(For the time being)
    public void displayExportMessage() {
        io.print("* * * * * * Feature Locked * * * * * *");
        io.print("Please Wait Until The Next Patch");
    }

    // Pausing the program to wait for the user to press a button
    public void displayPauseMessage() {
        io.readString("Please Press Enter To Continue.");
    }

    // Method to iterate through the order list, format it and display it
    public void displayOrders(List<Orders> orderList) {
        for (Orders order : orderList) {
            System.out.println("Order No." + order.getOrderNumber() + ", Customer: " + order.getCustomerName() + ", State: " + order.getState() +
                    ", TaxRate: " + order.getStateTaxRate() + "%, Product: " + order.getProductType() + ", " +
                    "Area: " + order.getArea() + "SqFt, CostPerSqFt: $" + order.getCostPerSqFoot() +
                    ", LabourCostPerSqFt: $" + order.getLabourPerSqFoot() + ", MaterialCost: $" + order.getMaterialCost() +
                    ", LabourCost: $" + order.getLabourCost() + ", Tax: $" + order.getTax() + ", Total: $" + order.getTotal());
        }
    }

    // Method to iterate through the product list, format it and display it
    public void displayProductList(List<Products> productsList) {
        for (Products product : productsList) {
            String productInfo = String.format("Product: %s, CostPerSqFoot: $%s, LabourCostPerSqFoot: $%s",
                    product.getProductType(),
                    product.getCostPerSqFoot(),
                    product.getLabourCostPerSqFoot());
            System.out.println(productInfo);
        }
    }

    // Method to iterate through the State list, format it and display it
    public void displayStateTaxes(List<StateTaxes> taxesList) {
        for (StateTaxes stateTax : taxesList) {
            String taxesInfo = String.format("State Code: %s, State: %s, Tax Rate: %s",
                    stateTax.getStateAlphaCode(),
                    stateTax.getStateName(),
                    stateTax.getStateTaxRate());
            System.out.println(taxesInfo);
        }
    }


}
