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
// THE SERVICE LAYER SHOULDN'T BE PRINTING TO THE CONSOLE
// (BUT I DON'T HAVE THE TIME TO FIX IT)

public class ServiceLayerImpl implements ServiceLayer {
    ProductDAO productDAO;
    StateTaxesDAO stateTaxesDAO;
    OrdersDAO ordersDAO;

    public ServiceLayerImpl(ProductDAO productDAO, StateTaxesDAO stateTaxesDAO, OrdersDAO ordersDAO) {
        this.productDAO = productDAO;
        this.stateTaxesDAO = stateTaxesDAO;
        this.ordersDAO = ordersDAO;
    }


    // Getting all the products from the file
    @Override
    public List<Products> getAllProducts() throws FilePersistenceException {
        return productDAO.getAllProducts();
    }

    // Getting all the taxes from the file
    @Override
    public List<StateTaxes> getAllStateTaxes() throws FilePersistenceException {
        return stateTaxesDAO.getAllStateTax();
    }

    // Method to get all orders from date given
    @Override
    public List<Orders> getAllOrders(String date) throws FilePersistenceException {
        return ordersDAO.getAllOrders(date);
    }


    // (LAMBDAS AND STREAMS could be used to read through the orders and get individual order)
    @Override
    public Orders getOrder(String date, int orderNumber) throws FilePersistenceException {
        // Get the orders from the date
        ordersDAO.getAllOrders(date);
        Orders wantedOrder = null;
        List<Orders> ordersList = getAllOrders(date);
        // Iterate through the Orders for
        for (Orders order : ordersList) {
            if (order.getOrderNumber() == orderNumber) {
                wantedOrder = order;
            }
        }
        return wantedOrder;
    }

    // Removing an error
    @Override
    public void removeOrder(String date, Orders order) throws FilePersistenceException {
        ordersDAO.removeOrder(date, order);
    }

    // Getting all the variables to create an order from the users edit
    @Override
    public Orders preEditOrder(String date, int orderNumber, String orderChanges) throws FilePersistenceException {
        List<Products> productList = getAllProducts();
        List<StateTaxes> allStateTax = getAllStateTaxes();

        // Getting the order to edit
        Orders orderWanted = getOrder(date, orderNumber);

        // Getting the variables from the user edits
        String changesArray[] = orderChanges.split(",");
        String newName = changesArray[0];
        String newState = changesArray[1];
        String newProduct = changesArray[2];
        Double newArea = Double.parseDouble(changesArray[3]);

        // Calculations for the edited variables
        BigDecimal trueArea = new BigDecimal(newArea);
        BigDecimal newMaterialCost = materialCost(newArea, newProduct);
        BigDecimal newLabourCost = labourCost(newArea, newProduct);
        BigDecimal newTax = tax(newMaterialCost, newLabourCost, newState);
        BigDecimal newTotal = total(newMaterialCost, newLabourCost, newTax);
        BigDecimal newStateTaxRate = null;
        BigDecimal newCostPerSqFt = null;
        BigDecimal newLabourPerSqFt = null;

        // setting the correct state tax, cost/labour cost per square foot price
        for (StateTaxes states : allStateTax) {
            if (newState.equals(states.getStateAlphaCode())) {
                newStateTaxRate = states.getStateTaxRate();
            }
        }
        for (Products products : productList) {
            if (newProduct.equals(products.getProductType())) {
                newCostPerSqFt = products.getCostPerSqFoot();
                newLabourPerSqFt = products.getLabourCostPerSqFoot();
            }
        }

        // Setting the order from the variables
        // (Doesn't create a new Orders object, just sets the different variables)
        // (Could be easier to create a New Orders object with the variables)
        orderWanted.setCustomerName(newName);
        orderWanted.setState(newState);
        orderWanted.setStateTaxRate(newStateTaxRate);
        orderWanted.setProductType(newProduct);
        orderWanted.setArea(trueArea);
        orderWanted.setCostPerSqFoot(newCostPerSqFt);
        orderWanted.setLabourPerSqFoot(newLabourPerSqFt);
        orderWanted.setMaterialCost(newMaterialCost);
        orderWanted.setLabourCost(newLabourCost);
        orderWanted.setTax(newTax);
        orderWanted.setTotal(newTotal);

        // Return the edited Orders object
        return orderWanted;
    }

    // Method for deleting the old order and placing the edited order in its place
    @Override
    public void editOrder(String date, Orders editedOrder, int orderNumber) throws FilePersistenceException {
        // Get old older and remove it from the file
        Orders oldOrder = getOrder(date, orderNumber);
        removeOrder(date, oldOrder);

        // Add in the edited order
        ordersDAO.newOrderSameFile(editedOrder, date);
    }

    // Method for verifying there is a file for a given date
    @Override
    public boolean createFileExists(String date) throws FilePersistenceException {
        boolean exist = ordersDAO.createFileExists(date);
        return exist;
    }

    // Verifying a given order number
    @Override
    public boolean verifyOrderNumber(String date, int orderNumber) throws FilePersistenceException {
        // Get all orders from the date
        ordersDAO.getAllOrders(date);
        boolean isValid = false;
        List<Orders> ordersList = getAllOrders(date);
        // Iterating through the orders and seeing if the order number is there
        for (Orders order : ordersList) {
            if (order.getOrderNumber() == orderNumber) {
                isValid = true;
            }
        }
        return isValid;
    }

    // Creating a new Orders Object -
    // (Would have been easier to Overload the Orders method to take less and set the other variables that way)
    @Override
    public void createNewOrder(String name, String state, String product, Double area, BigDecimal materialCost,
                               BigDecimal labourCost, BigDecimal tax, BigDecimal total, String date, boolean exists)
            throws FilePersistenceException {
        BigDecimal stateTaxRate = null;
        BigDecimal costPerSqFt = null;
        BigDecimal labourPerSqFt = null;
        List<Products> productList = getAllProducts();
        List<StateTaxes> allStateTax = getAllStateTaxes();

        // Setting the tax rate
        for (StateTaxes states : allStateTax) {
            if (state.equals(states.getStateAlphaCode())) {
                stateTaxRate = states.getStateTaxRate();
            }
        }
        // Setting the cost/labour cost per square root
        for (Products products : productList) {
            if (product.equals(products.getProductType())) {
                costPerSqFt = products.getCostPerSqFoot();
                labourPerSqFt = products.getLabourCostPerSqFoot();
            }
        }
        BigDecimal trueArea = new BigDecimal(area);
        // If there is already a file with the date, get the highest order number + 1
        if (exists) {
            int orderNumber = 0;
            List<Orders> ordersList = getAllOrders(date);
            for (Orders order : ordersList) {
                if (order.getOrderNumber() > orderNumber) {
                    orderNumber = order.getOrderNumber();
                }
            }
            orderNumber++;
            // Create the new Orders object
            Orders newOrder = new Orders(orderNumber, name, state, stateTaxRate, product, trueArea, costPerSqFt, labourPerSqFt,
                    materialCost, labourCost, tax, total);
            ordersDAO.newOrderSameFile(newOrder, date);
        }
        // If there is no file with the date, set the order number to 1 when creating the Orders object
        else {
            Orders newOrder = new Orders(1, name, state, stateTaxRate, product, trueArea, costPerSqFt, labourPerSqFt,
                    materialCost, labourCost, tax, total);
            ordersDAO.newOrderNewFile(newOrder, date);
        }

    }


    // Method to check if the date inputted is a valid date - THROWS DATETIMEPARSEEXCEPTION
    @Override
    public boolean validDate(String date) throws DateTimeParseException {
        boolean isValid = true;
        // Have to set the formatter to STRICT otherwise it tries to correct incorrect dates
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-d-uuuu").withResolverStyle(ResolverStyle.STRICT);
        // Try to parse the date, if it doesn't work print why
        try {
            LocalDate.parse(date, format);
        } catch (DateTimeParseException e) {
            isValid = false;
            System.out.println("Invalid date. \n" + e.getMessage());
        }
        return isValid;
    }

    // Method to verify if the date is in the past
    @Override
    public boolean pastDate(String date) throws DateTimeException {
        // Set the date format
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-d-uuuu").withResolverStyle(ResolverStyle.STRICT);
        // Parse the date
        LocalDate dateInQuestion = LocalDate.parse(date, format);
        // Verify if it's before today
        boolean inPast = dateInQuestion.isBefore(LocalDate.now());
        if (inPast) {
            System.out.println("Invalid Input. Date in the past.");
        }
        return inPast;
    }


    // Calculating Costs - SHOULD BE DONE IN ONE METHOD
    @Override
    public BigDecimal materialCost(double area, String product) throws FilePersistenceException {
        List<Products> productList = getAllProducts();
        BigDecimal costPerSqFt = null;
        for (Products products : productList) {
            if (product.equalsIgnoreCase(products.getProductType())) {
                costPerSqFt = products.getCostPerSqFoot();
            }
        }
        BigDecimal trueArea = new BigDecimal(area);
        BigDecimal materialCost = trueArea.multiply(costPerSqFt).setScale(2, RoundingMode.HALF_UP);
        return materialCost;

    }

    @Override
    public BigDecimal labourCost(double area, String product) throws FilePersistenceException {
        List<Products> productList = getAllProducts();
        BigDecimal labourCostPerSqFt = null;

        for (Products products : productList) {
            if (product.equalsIgnoreCase(products.getProductType())) {
                labourCostPerSqFt = products.getLabourCostPerSqFoot();
            }
        }

        BigDecimal trueArea = new BigDecimal(area);
        BigDecimal labourCost = trueArea.multiply(labourCostPerSqFt).setScale(2, RoundingMode.HALF_UP);
        return labourCost;
    }

    @Override
    public BigDecimal tax(BigDecimal materialCost, BigDecimal labourCost, String state)
            throws FilePersistenceException {
        BigDecimal tax = materialCost.add(labourCost);
        List<StateTaxes> allStateTax = getAllStateTaxes();
        for (StateTaxes states : allStateTax) {
            if (state.equalsIgnoreCase(states.getStateAlphaCode())) {
                tax = tax.multiply(states.getStateTaxRate()).setScale(2, RoundingMode.HALF_UP);
            }
        }
        tax = tax.divide(BigDecimal.valueOf(100.00)).setScale(2, RoundingMode.HALF_UP);
        return tax;
    }

    @Override
    public BigDecimal total(BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax) {
        BigDecimal total = materialCost.add(labourCost).add(tax);
        return total;
    }

}




