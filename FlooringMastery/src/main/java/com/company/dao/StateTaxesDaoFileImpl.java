package com.company.dao;

import com.company.model.Products;
import com.company.model.StateTaxes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

// This is only for getting a list of all the States and taxes
public class StateTaxesDaoFileImpl implements StateTaxesDAO {
    // Only Filepath that seems to work - Better than an absolute path
    private static final String TAXESFILE = "FlooringMastery-WileyEdge/FlooringMastery/SampleFileData/Data/Taxes.txt";
    private Map<String, Products> allTaxes = new HashMap<>();

    public void readTaxes() throws FilePersistenceException {
        Scanner scanner;


        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAXESFILE)));
        } catch (FileNotFoundException e) {
            throw new FilePersistenceException("- Unable to load Taxes - FILENOTFOUND");
        }

        // Need a String array that will hold the inputs of each file line
        String TaxInputs[];
        String inputline = " ";
        // Read the title line
        scanner.nextLine();
        // Read the file, Unmarshall Data, Create StateTaxes
        while (scanner.hasNextLine()) {
            inputline = scanner.nextLine();
            TaxInputs = inputline.split(",");
            String alphaCode = TaxInputs[0];
            String state = TaxInputs[1];
            String taxRate = TaxInputs[2];

            // Create StateTaxes and place them into allStateTaxes list
            StateTaxes tax = new StateTaxes(alphaCode, state, taxRate);

        }
        scanner.close();

    }


    @Override
    public List<StateTaxes> getAllStateTax() throws FilePersistenceException {
        readTaxes();
        return new ArrayList<StateTaxes>(StateTaxes.allStateTaxes.values());
    }
}
