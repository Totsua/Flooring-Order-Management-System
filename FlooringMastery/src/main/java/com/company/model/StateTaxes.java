package com.company.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class StateTaxes {
    private String stateAlphaCode;
    private String stateName;
    private BigDecimal stateTaxRate;
    public static Map<String, StateTaxes> allStateTaxes = new HashMap<>();

    // Constructor
    public StateTaxes(String stateAlphaCode, String stateName, String stateTaxRate) {
        this.stateAlphaCode = stateAlphaCode;
        this.stateName = stateName;
        // For all numbers, we need to set the scale for BigDecimal variables to 2
        this.stateTaxRate = new BigDecimal(stateTaxRate).setScale(2,RoundingMode.HALF_UP);
        // Place them into the allStateTaxes Map
        allStateTaxes.put(stateAlphaCode, this);
    }

    // Getters no Setters - Taxes is a READ ONLY file that won't be modified


    public String getStateAlphaCode() {
        return stateAlphaCode;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getStateTaxRate() {
        return stateTaxRate;
    }

    public static Map<String, StateTaxes> getAllStateTaxes() {
        return allStateTaxes;
    }

    @Override
    public String toString() {
        return "StateTaxes{" +
                "stateAlphaCode='" + stateAlphaCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", stateTaxRate=" + stateTaxRate +
                '}';
    }
}
