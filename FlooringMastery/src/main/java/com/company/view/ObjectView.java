package com.company.view;

import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.math.BigDecimal;
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
        return  io.readInt("Please Input An Option [1-6]", 1,6);
    }

    // MENU MESSAGES

    public void displayOrdersTitle(){
        io.print("* * * * * * * * * * * * Display Orders * * * * * * * * * * * * * * * *");
    }
    public String displayAskDate(){
        io.print("What Is The Date Of The Order:");
        String day = io.readDay("Please input the day of the order (DD): ",1,31);
        String month = io.readMonth("Please input the month of the order (MM): ", 1,12);
        String year = io.readYear("Please input the year of the order (YYYY): ");
        String orderDate = month+"-" +day+ "-" + year;
        boolean confirm = io.readChoice(orderDate+" (MM-DD-YYYY) is this correct?");
        if (confirm){
            return orderDate;
        } else{
            return null;
        }

    }

    public void displayAddOrderTitle(){
        io.print("* * * * * * * * * * * * * * Add An Order * * * * * * * * * * * * * * *");
    }
    public String getCustomerName(){return io.readName("Please Enter Your Name");}
    public String getCustomerState(){return io.readString("Please Enter The Alpha Code Of Your State: ");}
    public String getCustomerProduct(){return io.readString("Please Enter The Product You Want: ");}
    public Double getCustomerArea(){return io.readDouble("Please Enter The Area In SqFt (Min 100)",100);}
    public boolean displayCurrentOrder(String name, String state, String product, Double area, BigDecimal materialCost,
                               BigDecimal labourCost, BigDecimal tax, BigDecimal total){
        System.out.println("Customer: " +name+", State: "+state+", Product: "+product+", Area: "+area+
                "SqFt, MaterialCost: $"+materialCost+ ", LabourCost: $"+labourCost+", Tax: $"+tax+
                "\n + Total: $" +total);
        return io.readChoice("Do You Want To Place The Order?");
    }

    public void displayAddOrderSuccess(){
        io.print("* * * * * * * * * * * Order Added Successfully  * * * * * * * * * * * *");
    }


    public void displayRemoveOrderTitle(){
        io.print("* * * * * * * * * * * * Remove An Order * * * * * * * * * * * * * * * *");
    }
    public void displayRemoveSuccessMessage(){
        io.print("* * * * * * * * * * * * Remove Successful * * * * * * * * * * * * * * * *");
    }
    public boolean displayWantedOrder(Orders order){
        System.out.println("Order No."+order.getOrderNumber()+ ", Customer: "+ order.getCustomerName()+ ", State: "+order.getState()+
                ", TaxRate: "+order.getStateTaxRate()+ "%, Product: " +order.getProductType()+", " +
                "Area: "+order.getArea()+"SqFt, CostPerSqFt: $"+order.getCostPerSqFoot()+
                ", LabourCostPerSqFt: $"+order.getLabourPerSqFoot()+", MaterialCost: $"+order.getMaterialCost()+
                ", LabourCost: $"+order.getLabourCost()+", Tax: $"+order.getTax()+", Total: $"+order.getTotal());
        return io.readChoice("Do You Wish To Remove This Order? ");
    }
    public int askOrderNumber(){
       return io.readInt("Please Input The Order Number You Wish To Remove: ");
    }

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
        io.print("* * * * * * * * * * * * * * ERROR * * * * * * * * * * * * * * * * * *");
        io.print(errorMessage);
    }

    // "Export All Data" message -(For the time being)
    public void displayExportMessage(){
        io.print("* * * * * * Feature Locked * * * * * *");
        io.print("Please Wait Until The Next Patch");
    }
    public void displayPauseMessage(){
        io.readString("Please Press Enter To Continue.");
    }

    // Method to read through the order list, format it and display it
    public void displayOrders(List<Orders> orderList){
        for (Orders order: orderList){
            System.out.println("Order No."+order.getOrderNumber()+ ", Customer: "+ order.getCustomerName()+ ", State: "+order.getState()+
                    ", TaxRate: "+order.getStateTaxRate()+ "%, Product: " +order.getProductType()+", " +
                    "Area: "+order.getArea()+"SqFt, CostPerSqFt: $"+order.getCostPerSqFoot()+
                    ", LabourCostPerSqFt: $"+order.getLabourPerSqFoot()+", MaterialCost: $"+order.getMaterialCost()+
                    ", LabourCost: $"+order.getLabourCost()+", Tax: $"+order.getTax()+", Total: $"+order.getTotal());
        }
    }

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
