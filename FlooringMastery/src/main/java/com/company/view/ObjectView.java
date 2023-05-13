package com.company.view;

import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.util.List;

public class ObjectView {

    private UserIO io;
    public  ObjectView(UserIO io) {this.io = io;}

    public int PrintMenuAndGetChoice(){
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
        return  io.readInt("Please input an option [1-6]", 1,6);
    }

    // MENU MESSAGES

    // "Quit" menu option message
    public void displayExitMessage() {
        io.print("Thank You For Using This Service!");
        io.print("Goodbye!");}

    // Unknown command message
    public void displayUnknownCommandMessage(){
        io.print("Command Unknown");
    }

    // Error Message
    public void displayErrorMessage(String errorMessage){
        io.print("******ERROR******");
        io.print(errorMessage);
    }

    // "Export All Data" message -(For the time being)
    public void displayExportMessage(){
        io.print("******Feature Locked******");
        io.print("Please Wait Until The Next Patch");
    }

    public void displayOrders(List<Orders> orderList){
        for (Orders order: orderList){
           // String orderInfo = String.format("Order No.%s, Customer: %s, State: %s, TaxRate: %s ls@  ")
            System.out.println("Order No."+order.getOrderNumber()+ ", Customer: "+ order.getCustomerName()+ ", State: "+order.getState()+
                    ", TaxRate: "+order.getStateTaxRate()+ "%, Product: " +order.getProductType()+", " +
                    "Area: "+order.getArea()+"SqFt, CostPerSqFt: $"+order.getCostPerSqFoot()+
                    ", LabourCostPerSqFt: $"+order.getLabourPerSqFoot()+", MaterialCost: $"+order.getMaterialCost()+
                    ", LabourCost: $"+order.getLabourCost()+", Tax: $"+order.getTax()+", Total:$"+order.getTotal());
                    //nteger orderNumber, String customerName, String state, BigDecimal stateTaxRate,
            //                  String productType, BigDecimal area, BigDecimal costPerSqFoot, BigDecimal labourPerSqFoot,
            //                  BigDecimal materialCost, BigDecimal labourCost, BigDecimal total)
        }
    }


    //Unnecessary for now
    public void displayProductList(List<Products> productsList) {
        for (Products product : productsList) {
            String productInfo = String.format("Product: %s, CostPerSqFoot: $%s, LabourCostPerSqFoot: $%s",
                    product.getProductType(),
                    product.getCostPerSqFoot(),
                    product.getLabourCostPerSqFoot());
            System.out.println(productInfo);
        }
    }

    //Unnecessary
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
