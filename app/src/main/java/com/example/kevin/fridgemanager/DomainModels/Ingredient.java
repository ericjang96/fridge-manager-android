package com.example.kevin.fridgemanager.DomainModels;

import java.io.Serializable;
import java.util.Date;

public class Ingredient implements Serializable {
    private String name;
    private Date boughtDate;
    private Date expiryDate;
    private String amountUnit;
    private Integer amount;

    public Ingredient(String name, String amtUnit, Integer amt){
        this.name = name;
        amountUnit = amtUnit;
        amount = amt;
    }

    public Ingredient(String name, Date bought, Date expire, String amtUnit, Integer amt){
        this.name = name;
        boughtDate = bought;
        expiryDate = expire;
        amountUnit = amtUnit;
        amount = amt;
    }

    public String getName(){
        return name;
    }

    public Date getBoughtDate(){
        return boughtDate;
    }

    public Date getExpiryDate(){
        return expiryDate;
    }

    public String getUnit(){
        return amountUnit;
    }

    public int getAmount(){
        return amount;
    }

    public String getAmountString() {
        return amount.toString();
    }


    public void setName(String newName){
        this.name = newName;
    }

    public void setAmount(int newAmt){
        this.amount = newAmt;
    }

    public void setAmountUnit(String newUnit){
        this.amountUnit = newUnit;
    }
}