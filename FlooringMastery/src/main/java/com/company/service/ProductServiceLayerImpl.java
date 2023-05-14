package com.company.service;

import com.company.dao.*;
import com.company.model.Orders;
import com.company.model.Products;
import com.company.model.StateTaxes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

// Used as the in-between for the Controller and DAO

public class ProductServiceLayerImpl implements ProductServiceLayer {
    ProductDAO dao;
    StateTaxesDAO dao2;
    OrdersDAO dao3;

    public ProductServiceLayerImpl(ProductDAO dao, StateTaxesDAO dao2, OrdersDAO dao3) {
        this.dao = dao;
        this.dao2 = dao2;
        this.dao3 = dao3;
    }

    @Override
    public List<Products> getAllProducts() throws FilePersistenceException {
        return dao.getAllProducts();
    }

    @Override
    public List<StateTaxes> getAllStateTaxes() throws FilePersistenceException {
        return dao2.getAllStateTax();
    }

    // Method to get all orders from certain date
    @Override
    public List<Orders> getAllOrders(String date) throws FilePersistenceException {
        return dao3.getAllOrders(date);
    }

    @Override
    public boolean createFileExists(String date)throws FilePersistenceException{
        boolean exist = dao3.createFileExists(date);
        return exist;
    }

    // Creating a new Orders Object -
    // (Would have been easier to Overload the Orders method to take less and set the other variables that way)
    @Override
    public void createNewOrder(String name, String state, String product, Double area, BigDecimal materialCost,
                               BigDecimal labourCost, BigDecimal tax, BigDecimal total, String date, boolean exists)
    //Integer orderNumber, String customerName, String state, BigDecimal stateTaxRate,
    //                  String productType, BigDecimal area, BigDecimal costPerSqFoot, BigDecimal labourPerSqFoot,
    //                  BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax, BigDecimal total)
            throws FilePersistenceException
    {
        BigDecimal stateTaxRate = null;
        BigDecimal costPerSqFt = null;
        BigDecimal labourPerSqFt = null;
        List<Products> productList = getAllProducts();
        List<StateTaxes> allStateTax = getAllStateTaxes();

        for(StateTaxes states: allStateTax){
            if(state.equals(states.getStateAlphaCode())){
                stateTaxRate =states.getStateTaxRate();
            }
        }
        for(Products products : productList){
            if (product.equals(products.getProductType())){
                costPerSqFt = products.getCostPerSqFoot();
                labourPerSqFt = products.getLabourCostPerSqFoot();
            }
        }
        BigDecimal trueArea = new BigDecimal(area);
        if(exists){
            int orderNumber = 0;
            List<Orders> ordersList = getAllOrders(date);
            for (Orders order: ordersList){
                if (order.getOrderNumber() > orderNumber){
                    orderNumber = order.getOrderNumber();
                }
            }
            orderNumber++;
            Orders newOrder = new Orders( orderNumber,name, state, stateTaxRate, product, trueArea, costPerSqFt, labourPerSqFt,
                    materialCost, labourCost, tax, total);
            dao3.newOrderSameFile(newOrder,date);
        }
        else{
            Orders newOrder = new Orders( 1,name, state, stateTaxRate, product, trueArea, costPerSqFt, labourPerSqFt,
                    materialCost, labourCost, tax, total);
            //Integer orderNumber, String customerName, String state, BigDecimal stateTaxRate,
            //                  String productType, BigDecimal area, BigDecimal costPerSqFoot, BigDecimal labourPerSqFoot,
            //                  BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax, BigDecimal total)
            dao3.newOrderNewFile(newOrder,date);
        }

    }




    // Method to check if the date inputted is a valid date
    @Override
    public boolean validDate(String date) throws DateTimeParseException {
        boolean isValid = true;
        // Have to set the formatter to STRICT otherwise it tries to correct incorrect dates
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-d-uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(date,format);
        } catch (DateTimeParseException e) {
            isValid = false;
            System.out.println("Invalid date. \n" + e.getMessage());
        }
        return isValid;
    }

    @Override
    public boolean pastDate(String date) throws DateTimeException{
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-d-uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate dateInQuestion = LocalDate.parse(date,format);
       boolean inPast = dateInQuestion.isBefore(LocalDate.now());
       if(inPast){
           System.out.println("Invalid Input. Date in the past.");
       }
        return inPast;
    }

    @Override
    public boolean validateState(String state) throws FilePersistenceException{
        boolean isValid = dao2.validateState(state);
        if (!isValid){
            System.out.println("Invalid Input. State Code Not Found.");
        }
        return isValid;
    }

    @Override
    public boolean validateProduct(String product) throws FilePersistenceException{
        boolean isValid = dao.validateProduct(product);
        if (!isValid){
            System.out.println("Input Error. Product Not Found.");
            System.out.println("Please Input The Product As Shown.");
        }
        return isValid;
    }

    @Override
    public BigDecimal materialCost(double area,String product) throws FilePersistenceException {
        List<Products> productList = getAllProducts();
        BigDecimal costPerSqFt = null;
        for(Products products : productList){
            if (product.equals(products.getProductType())){
                costPerSqFt = products.getCostPerSqFoot();
            }
        }
        BigDecimal trueArea = new BigDecimal(area);
        BigDecimal materialCost = trueArea.multiply(costPerSqFt).setScale(2,RoundingMode.HALF_UP);
        return materialCost;

    }

    @Override
    public BigDecimal labourCost(double area, String product) throws FilePersistenceException {
        List<Products> productList = getAllProducts();
        BigDecimal labourCostPerSqFt = null;

        for (Products products: productList){
            if (product.equals(products.getProductType())){
                labourCostPerSqFt = products.getLabourCostPerSqFoot();}
        }

        BigDecimal trueArea = new BigDecimal(area);
        BigDecimal labourCost = trueArea.multiply(labourCostPerSqFt).setScale(2,RoundingMode.HALF_UP);
        return labourCost;
    }

    @Override
    public BigDecimal tax (BigDecimal materialCost, BigDecimal labourCost, String state) throws FilePersistenceException{
        BigDecimal tax= materialCost.add(labourCost);
        List<StateTaxes> allStateTax = getAllStateTaxes();
        for(StateTaxes states: allStateTax){
            if(state.equals(states.getStateAlphaCode())){
                tax =tax.multiply(states.getStateTaxRate()).setScale(2,RoundingMode.HALF_UP);
            }
        }
        tax = tax.divide(BigDecimal.valueOf(100.00)).setScale(2,RoundingMode.HALF_UP);
        return tax;
    }
    @Override
   public BigDecimal total (BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax){
        BigDecimal total = materialCost.add(labourCost).add(tax);
        return total;
   }

}




