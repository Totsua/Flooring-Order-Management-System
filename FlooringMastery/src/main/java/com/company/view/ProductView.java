package com.company.view;

import com.company.model.Products;

import java.util.List;

public class ProductView {
    public void displayProductList(List<Products> productsList){
        for (Products product : productsList){
            String productInfo = String.format("Product: %s, CostPerSqFoot: $%s, LabourCostPerSqFoot: $%s",
                    product.getProductType(),
                    product.getCostPerSqFoot(),
                    product.getLabourCostPerSqFoot());
            System.out.println(productInfo);
        }
    }
}
