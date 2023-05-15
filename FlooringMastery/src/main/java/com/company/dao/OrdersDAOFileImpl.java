package com.company.dao;

import com.company.model.Orders;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class OrdersDAOFileImpl implements OrdersDAO {
    // Title line for files
    private static final String FILETITLELINE = "OrderNumber,CustomerName,State,TaxRate,ProductType" +
            ",Area,CostPerSquareFoot,LaborCostPerSquareFoot," +
            "MaterialCost,LaborCost,Tax,Total";
    // allDateOrders is always cleared before being used - Stops orders staying on memory
    public Map<Integer, Orders> allDateOrders = new HashMap<>();

    // Method to verify if a file exists with given date
    @Override
    public boolean createFileExists(String date) throws FilePersistenceException {
        String fileName = createFileName(date);
        boolean createFile = true;
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
            scanner.close();
        } catch (FileNotFoundException e) {
            createFile = false;
            scanner = null;
        }
        return createFile;
    }

    // Method to add a new order in a new file
    @Override
    public void newOrderNewFile(Orders order, String date) throws FilePersistenceException {
        // Clear allDateOrders
        allDateOrders.clear();
        // Place the order into allDateOrders
        allDateOrders.put(order.getOrderNumber(), order);
        //Create the file name
        String fileName = createFileName(date);
        // Write to the file
        writeOrders(fileName);

    }


    // Method to create the file name using a given date
    public String createFileName(String date) {
        // Split the date into an array
        String orderSplit[] = date.split("-");
        // Set the date format into a string
        String dateFormat = orderSplit[0] + orderSplit[1] + orderSplit[2];
        // Program seems to only read file if filepath is as the following:
        String fileName = "FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Orders/Orders_" + dateFormat + ".txt";
        return fileName;
    }

    // Method for writing a new order on an Existing file
    @Override
    public void newOrderSameFile(Orders order, String date) throws FilePersistenceException {
        // Clear allDateOrders
        allDateOrders.clear();
        // Create file name string
        String fileName = createFileName(date);
        // Read the orders from the file/ add them to allDateOrders
        readOrders(fileName);
        allDateOrders.put(order.getOrderNumber(), order);
        // Write the new allDateOrders to the file
        writeOrders(fileName);
    }

    // Method to remove files
    @Override
    public void removeOrder(String date, Orders order) throws FilePersistenceException {
        // Clear allDateOrders
        allDateOrders.clear();
        // Create file name string
        String fileName = createFileName(date);
        // Read the orders from the file and delete the chosen one
        readOrders(fileName);
        allDateOrders.remove(order.getOrderNumber());
        // Write the rest to the file
        writeOrders(fileName);
    }


    // Method to read the Orders from a file
    public void readOrders(String fileName) throws FilePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FilePersistenceException("- Unable To Load Orders - ORDERDATE Does Not Exist");
        }

        // Need a String array that will hold the inputs of each file line
        String inputline = " ";
        Orders currentOrder;
        // Read the title line
        scanner.nextLine();
        // Read the file, Unmarshall Data, Create Orders
        while (scanner.hasNextLine()) {
            inputline = scanner.nextLine();

            // Unmarshall line and create an Order object.
            currentOrder = unmarshallOrder(inputline);

            // Place the newly created Order in allDateOrders
            allDateOrders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();

    }

    // Method to write orders to the file
    private void writeOrders(String fileName) throws FilePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FilePersistenceException("Could Not Save Order Data", e);
        }
        // Write the File title line
        out.println(FILETITLELINE);
        String orderAsText;
        // Iterate through the orders and marshall them
        List<Orders> allOrders = this.getAllOrders();
        for (Orders order : allOrders) {
            // Turn order into a string
            orderAsText = marshallOrder(order);
            // Write the Order into the file
            out.println(orderAsText);
            // Force PrintWriter to write the line to the file
            out.flush();
        }
        out.close(); //  Close the PrintWriter
    }


    // A method to unmarshall data
    private Orders unmarshallOrder(String inputline) {
        // Split the orders into an array
        String orderInputs[] = inputline.split(",");
        // Set the values
        String orderNumberString = orderInputs[0];
        String customerName = orderInputs[1];
        String state = orderInputs[2];
        String stateTaxRate = orderInputs[3];
        String productType = orderInputs[4];
        String area = orderInputs[5];
        String costPerSqFoot = orderInputs[6];
        String labourPerSqFoot = orderInputs[7];
        String materialCost = orderInputs[8];
        String labourCost = orderInputs[9];
        String tax = orderInputs[10];
        String total = orderInputs[11];

        // Convert the variables that need converting
        Integer orderNumber = Integer.parseInt(orderNumberString);
        BigDecimal trueStateTax = new BigDecimal(stateTaxRate).setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal trueArea = new BigDecimal(area).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueCostSqFt = new BigDecimal(costPerSqFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueLabourSqFtCost = new BigDecimal(labourPerSqFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueMaterialCost = new BigDecimal(materialCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueLabourCost = new BigDecimal(labourCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueTax = new BigDecimal(tax).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueTotal = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);

        // Create and return an Orders object
        Orders newOrder = new Orders(orderNumber, customerName, state, trueStateTax, productType, trueArea, trueCostSqFt,
                trueLabourSqFtCost, trueMaterialCost, trueLabourCost, trueTax, trueTotal);
        return newOrder;

    }

    // Method for marshalling data
    private String marshallOrder(Orders order) {
        // All we need is to get out each property and concatenate with our DELIMITER as a spacer.
        String delimiter = ",";
        String orderAsString = String.valueOf(order.getOrderNumber()) + delimiter;
        orderAsString += String.valueOf(order.getCustomerName()) + delimiter; // First Name
        orderAsString += String.valueOf(order.getState()) + delimiter; // Last Name
        orderAsString += String.valueOf(order.getStateTaxRate()) + delimiter;
        orderAsString += String.valueOf(order.getProductType()) + delimiter;
        orderAsString += String.valueOf(order.getArea()) + delimiter;
        orderAsString += String.valueOf(order.getCostPerSqFoot()) + delimiter;
        orderAsString += String.valueOf(order.getLabourPerSqFoot()) + delimiter;
        orderAsString += String.valueOf(order.getMaterialCost()) + delimiter;
        orderAsString += String.valueOf(order.getLabourCost()) + delimiter;
        orderAsString += String.valueOf(order.getTax()) + delimiter;
        orderAsString += String.valueOf(order.getTotal());
        // No need for a delimiter as cohort the last property.

        return orderAsString; // Have to return the String
    }

    // Method for getting all orders in a file
    @Override
    public List<Orders> getAllOrders(String date) throws FilePersistenceException {
        String fileName = createFileName(date);
        allDateOrders.clear();
        readOrders(fileName);
        return new ArrayList<Orders>(allDateOrders.values());
    }

    // Method for getting all orders in this class
    @Override
    public List<Orders> getAllOrders() throws FilePersistenceException {
        return new ArrayList<Orders>(allDateOrders.values());
    }
}
