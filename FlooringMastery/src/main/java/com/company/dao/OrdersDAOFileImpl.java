package com.company.dao;

import com.company.model.Orders;
import com.company.model.Products;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class OrdersDAOFileImpl implements OrdersDAO{
        private static final String ORDERSFILE = "FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Orders/Orders_06022013.txt";
                                                    //FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Data/Products.txt
        private static final String DELIMITER = ",";
        public Map<Integer, Orders> allDateOrders = new HashMap<>();



        // Method to read the Orders from a file
    public void readOrders() throws FilePersistenceException {
            Scanner scanner;


            try {
                scanner = new Scanner(new BufferedReader(new FileReader(ORDERSFILE)));
            } catch (FileNotFoundException e) {
                throw new FilePersistenceException("- Unable to load Orders - ORDERDATE doesn't exist");
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

        // A method to take the line from the file, split it into the appropriate variables and create an Order Object

        private Orders unmarshallOrder (String inputline){
            String orderInputs[] =inputline.split(",");
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

            Orders newOrder = new Orders(orderNumber,customerName, state,trueStateTax,productType,trueArea,trueCostSqFt,
                                            trueLabourSqFtCost,trueMaterialCost,trueLabourCost,trueTax,trueTotal);
            return newOrder;

        }


    @Override
    public List<Orders> getAllOrders() throws FilePersistenceException {
        readOrders();
        return new ArrayList<Orders>(allDateOrders.values());
    }
}

