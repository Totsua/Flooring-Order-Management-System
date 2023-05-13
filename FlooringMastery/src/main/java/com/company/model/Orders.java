package com.company.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Orders {
    private Integer orderNumber;
    private String customerName;
    private String state;
    private BigDecimal stateTaxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSqFoot;
    private BigDecimal labourPerSqFoot;
    private BigDecimal materialCost;
    private BigDecimal labourCost;
    private BigDecimal tax;
    private BigDecimal total;
    private String orderDate;
    public static Map <String, Orders> allOrdersPerDate = new HashMap<>();

    // Constructor

    public Orders(Integer orderNumber, String customerName, String state, BigDecimal stateTaxRate,
                  String productType, BigDecimal area, BigDecimal costPerSqFoot, BigDecimal labourPerSqFoot,
                  BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax, BigDecimal total) {
        // Convert numbers to BigDecimal
        // this.stateTaxRate = new BigDecimal(stateTaxRate).setScale(2,RoundingMode.HALF_UP);

        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.stateTaxRate = stateTaxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSqFoot = costPerSqFoot;
        this.labourPerSqFoot = labourPerSqFoot;
        this.materialCost = materialCost;
        this.labourCost = labourCost;
        this.tax = tax;
        this.total = total;
    }

    // Getters and Setters
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getStateTaxRate() {
        return stateTaxRate;
    }

    public void setStateTaxRate(BigDecimal stateTaxRate) {
        this.stateTaxRate = stateTaxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSqFoot() {
        return costPerSqFoot;
    }

    public void setCostPerSqFoot(BigDecimal costPerSqFoot) {
        this.costPerSqFoot = costPerSqFoot;
    }

    public BigDecimal getLabourPerSqFoot() {
        return labourPerSqFoot;
    }

    public void setLabourPerSqFoot(BigDecimal labourPerSqFoot) {
        this.labourPerSqFoot = labourPerSqFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(BigDecimal labourCost) {
        this.labourCost = labourCost;
    }

    public BigDecimal getTax() {return tax;}

    public void setTax(BigDecimal tax) {this.tax = tax;}

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
