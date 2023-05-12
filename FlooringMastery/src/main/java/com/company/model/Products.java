package com.company.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Products {
    private String productType;
    private BigDecimal costPerSqFoot;
    private BigDecimal labourCostPerSqFoot;
    public static Map <String, Products> allProducts = new HashMap<>();

    // Constructor
    public Products(String productType, String costPerSqFoot, String labourCostPerSqFoot) {
        this.productType = productType;
        // As we're using currency, we need to set the scale for BigDecimal variables to 2
        this.costPerSqFoot = new BigDecimal(costPerSqFoot).setScale(2, RoundingMode.HALF_UP);
        this.labourCostPerSqFoot = new BigDecimal(labourCostPerSqFoot).setScale(2, RoundingMode.HALF_UP);
        // Place automatically in the allProducts Map
        allProducts.put(productType, this);
    }

    // Getters no Setters, Products are a READ ONLY file
    // Potentially may need Setters for Orders?

    public String getProductType() {
        return productType;
    }


    public BigDecimal getCostPerSqFoot() {
        return costPerSqFoot;
    }



    public BigDecimal getLabourCostPerSqFoot() {
        return labourCostPerSqFoot;
    }

    // toString
    @Override
    public String toString() {
        return "Products{" +
                "productType='" + productType + '\'' +
                ", costPerSqFoot=" + costPerSqFoot +
                ", labourCostPerSqFoot=" + labourCostPerSqFoot +
                '}';
    }
}

