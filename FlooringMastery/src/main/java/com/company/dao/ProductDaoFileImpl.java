package com.company.dao;

import com.company.model.Products;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ProductDaoFileImpl implements ProductDao {
    private static final String PRODUCTSFILE = "SampleFileData/Data/Products.txt";
    private static final String DELIMITER = ",";
    private Map<String, Products> allProducts = new HashMap<>();
    public void readProducts() throws ProductPersistenceException {
        File file = new File(PRODUCTSFILE);
        FileReader fileReader;
        Scanner scanner;


        try{
            scanner = new Scanner(new BufferedReader (new FileReader(PRODUCTSFILE)));
        }catch (FileNotFoundException e){
            throw new ProductPersistenceException("- Unable to load products - FILENOTFOUND");
        }

        // Need a String array that will hold the inputs of each file line
        String productInputs[];
        String inputline= " ";
        // Read the title line
        scanner.nextLine();
        // Read the file, Unmarshall Data, Create Products
        while(scanner.hasNextLine()){
            inputline = scanner.nextLine();
            productInputs = inputline.split(",");
            //System.out.println(productInputs.length);
            String productType = productInputs[0];
            String costPerSqMtr = productInputs[1];
            String labourPerSqMtr = productInputs[2];

            // Create products and place them into allProducts list
            Products product = new Products(productType, costPerSqMtr, labourPerSqMtr);

        }
        scanner.close();

    }

    /*private void loadProducts() throws ProductPersistenceException{
        Scanner scanner;

        try{
            scanner = new Scanner(new BufferedReader(new FileReader()))
        }
    }*/

    @Override
    public List<Products> getAllProducts() throws ProductPersistenceException {
        readProducts();
        return new ArrayList<Products>(Products.allProducts.values());
       /* Products hmm;
        return new ArrayList<Products>(allProducts.values());*/
    }
}
