package com.example.arsenii.bks.model.entity;

/**
 * Created by arsenii on 03.12.17.
 */

public class FinanceData {

    private String title;
    private double value;

    public FinanceData(String title, double value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }
}
