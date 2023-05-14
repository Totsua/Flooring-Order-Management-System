package com.company.dao;

import com.company.model.Orders;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class OrdersDAOFileImpl implements OrdersDAO {
    private static String ORDERSFILE = "FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Orders/Orders_06022013.txt";
    //FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Data/Products.txt
    private static final String DELIMITER = "-";
    private static final String FILETITLELINE = "OrderNumber,CustomerName,State,TaxRate,ProductType" +
            ",Area,CostPerSquareFoot,LaborCostPerSquareFoot," +
            "MaterialCost,LaborCost,Tax,Total";
    public Map<Integer, Orders> allDateOrders = new HashMap<>();

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

    @Override
    public void newOrderNewFile(Orders order, String date) throws FilePersistenceException {
        allDateOrders.put(order.getOrderNumber(), order);

        String[] splitDate = date.split(DELIMITER);
        String month = splitDate[0];
        String day = splitDate[1];
        String year = splitDate[2];
        String dateFormat = month + day + year;
        String fileName = "FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Orders/Orders_" + dateFormat + ".txt";
        writeOrders(fileName);

    }


    // Method to create the file name using a given date
    public String createFileName(String date) {   //,boolean create{
        String orderSplit[] = date.split("-");
        String dateFormat = orderSplit[0] + orderSplit[1] + orderSplit[2];
        // Program seems to only read file if filepath is as the following:
        String fileName = "FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Orders/Orders_" + dateFormat + ".txt";
        return fileName;
    }

    @Override
    public void newOrderSameFile (Orders order, String date) throws FilePersistenceException{
        //String fileName = createFileName(date);
        String[] splitDate = date.split(DELIMITER);
        String month = splitDate[0];
        String day = splitDate[1];
        String year = splitDate[2];
        String dateFormat = month + day + year;
        String fileName = "FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Orders/Orders_" + dateFormat + ".txt";
        //writeOrders(fileName);
        readOrders(fileName);
        allDateOrders.put(order.getOrderNumber(), order);
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FilePersistenceException("Could Not Save Order Data", e);
        }
            // Write the title line first into the file
            out.println(FILETITLELINE);
            // Force PrintWriter to write the line to the file
            out.flush();
        out.close();
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

            // Place the newly created Order in the
            allDateOrders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();

    }

    private void writeOrders(String fileName) throws FilePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FilePersistenceException("Could Not Save Order Data", e);
        }

        String orderAsText;
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


    // A method to take the line from the file, split it into the appropriate variables and create an Order Object

    private Orders unmarshallOrder(String inputline) {
        String orderInputs[] = inputline.split(",");
        //System.out.println(productInputs.length);
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

        Integer orderNumber = Integer.parseInt(orderNumberString);
        BigDecimal trueStateTax = new BigDecimal(stateTaxRate).setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal trueArea = new BigDecimal(area).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueCostSqFt = new BigDecimal(costPerSqFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueLabourSqFtCost = new BigDecimal(labourPerSqFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueMaterialCost = new BigDecimal(materialCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueLabourCost = new BigDecimal(labourCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueTax = new BigDecimal(tax).setScale(2, RoundingMode.HALF_UP);
        BigDecimal trueTotal = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);

        Orders newOrder = new Orders(orderNumber, customerName, state, trueStateTax, productType, trueArea, trueCostSqFt,
                trueLabourSqFtCost, trueMaterialCost, trueLabourCost, trueTax, trueTotal);
        return newOrder;

    }

    private String marshallOrder(Orders order) {
        // This is for turning the Orders str
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

    @Override
    public List<Orders> getAllOrders(String date) throws FilePersistenceException {
        String fileName = createFileName(date);
        readOrders(fileName);
        return new ArrayList<Orders>(allDateOrders.values());
    }

    @Override
    public List<Orders> getAllOrders() throws FilePersistenceException {
        return new ArrayList<Orders>(allDateOrders.values());
    }
}
